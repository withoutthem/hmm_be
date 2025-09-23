/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpResources;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

@Slf4j
@Configuration
public class WebClientConfig {
  private LoopResources loopResources;

  @Value("${server.port}")
  private String baseUrl;

  @Bean
  public WebClient webClient() {

    log.debug("webClient start");

    // ObjectMapper 설정
    ObjectMapper mapper =
        new ObjectMapper()
            .configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true)
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

    // ExchangeStrategies 설정
    ExchangeStrategies strategies =
        ExchangeStrategies.builder()
            .codecs(
                configurer -> {
                  configurer
                      .defaultCodecs()
                      .jackson2JsonEncoder(
                          new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));
                  configurer
                      .defaultCodecs()
                      .jackson2JsonDecoder(
                          new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
                  //                    configurer.defaultCodecs().maxInMemorySize(16 * 1024 *
                  // 1024); // 16MB
                })
            .build();

    int cores = Runtime.getRuntime().availableProcessors();
    this.loopResources = LoopResources.create("custom-event-loop", 4, cores * 2, true);

    // 기본 LoopResources
    HttpResources.set(loopResources);

    /*
     * 통신시 timeout 세팅
     * - connect, read, write 를 모두 5000ms
     */
    HttpClient httpClient =
        HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(
                conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)))
            // 요청 라인 및 헤더 크기 제한 늘리기
            .httpResponseDecoder(
                spec ->
                    spec.maxInitialLineLength(16 * 1024) // 16 KB (기본값: 4KB)
                        .maxHeaderSize(32 * 1024)) // 32 KB (기본값: 8 KB)
            .runOn(loopResources);

    // 생성한 HttpClient 연결
    // Request Header 로깅 필터
    // Response Header 로깅 필터
    // 기본 헤더설정

    return WebClient.builder()
        .baseUrl("localhost:" + baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(httpClient)) // 생성한 HttpClient 연결
        // Request Header 로깅 필터
        .filter(
            ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                  log.info(">>>>>>>>> REQUEST <<<<<<<<<<");
                  log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
                  clientRequest
                      .headers()
                      .forEach(
                          (name, values) ->
                              values.forEach(value -> log.info("{} : {}", name, value)));
                  return Mono.just(clientRequest);
                }))
        // Response Header 로깅 필터
        .filter(
            ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                  log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");
                  log.info("Response Status :  {} ", clientResponse.statusCode());
                  clientResponse
                      .headers()
                      .asHttpHeaders()
                      .forEach(
                          (name, values) ->
                              values.forEach(value -> log.info("{} : {}", name, value)));
                  return Mono.just(clientResponse);
                }))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 기본 헤더설정
        .exchangeStrategies(strategies)
        .build();
  }

  @PreDestroy
  public void shutdownLoop() {
    // 애플리케이션 종료 시 LoopResources 종료
    if (loopResources != null) {
      loopResources.disposeLater().block();
    }
  }
}
