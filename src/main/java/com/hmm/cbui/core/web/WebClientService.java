/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.web;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

  private final WebClient webClient;

  /**
   * 공통 GET 요청 메서드
   *
   * @param uri 요청할 URI 경로
   * @param responseType 응답 타입
   * @param <T> 응답 타입 제네릭
   * @return Mono<T> 타입의 응답
   */
  public <T> Mono<T> get(String uri, Class<T> responseType) {
    return webClient
        .get()
        .uri(uri)
        .retrieve()
        .bodyToMono(responseType) // JSON 응답을 DTO로 변환
        .onErrorResume(
            WebClientResponseException.class,
            ex -> {
              // 공통적인 에러 핸들링 로직
              log.error("API 호출 중 오류 발생: {}", ex.getMessage());
              return Mono.error(new RuntimeException("API 호출 실패"));
            });
  }

  /**
   * 공통 POST 요청 메서드
   *
   * @param uri 요청할 URI 경로
   * @param request 요청 본문
   * @param responseType 응답 타입
   * @param <T> 응답 타입 제네릭
   * @return Mono<T> 타입의 응답
   */
  public <T> Mono<T> post(String uri, Object request, Class<T> responseType) {
    Map<String, String> headers = new HashMap<>();
    //        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    headers.put("Accept", MediaType.APPLICATION_JSON_VALUE + ", text/plain, */*");
    headers.put("Accept-Encoding", "gzip, deflate");
    headers.put("Accept-Language", "ko,en;q=0.9,en-US;q=0.8");

    return webClient
        .post()
        .uri(uri)
        .headers(
            httpHeaders -> {
              headers.forEach(httpHeaders::set);
              httpHeaders.set(
                  HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
            })
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(responseType) // JSON 응답을 DTO로 변환
        .onErrorResume(
            WebClientResponseException.class,
            ex -> {
              // 공통적인 에러 핸들링 로직
              log.error("API 호출 중 오류 발생: {}", ex.getMessage());
              return Mono.error(new RuntimeException("API 호출 실패"));
            });
  }

  /**
   * 공통 POST 요청 메서드 (헤더 지원)
   *
   * @param uri 요청할 URI 경로
   * @param request 요청 본문
   * @param headers 요청 헤더
   * @param responseType 응답 타입
   * @param <T> 응답 타입 제네릭
   * @return Mono<T> 타입의 응답
   */
  public <T> Mono<T> post(
      String uri, Object request, Map<String, String> headers, Class<T> responseType) {
    return webClient
        .post()
        .uri(uri)
        .headers(httpHeaders -> headers.forEach(httpHeaders::set)) // 커스텀 헤더 추가
        .body(Mono.just(request), Object.class) // 요청 데이터를 본문에 포함
        .retrieve()
        .bodyToMono(responseType) // JSON 응답을 DTO로 변환
        .onErrorResume(
            WebClientResponseException.class,
            ex -> {
              // 공통적인 에러 핸들링 로직
              log.error("API 호출 중 오류 발생: {}", ex.getMessage());
              return Mono.error(new RuntimeException("API 호출 실패"));
            });
  }

  /**
   * POST 요청 (버퍼 처리)
   *
   * @param url 요청할 URI 경로
   * @param requestBody 요청 본문 (JSON)
   * @return Flux<DataBuffer> 타입의 응답
   *     <p>이 메소드는 대용량 데이터를 버퍼 단위로 전송받는 방식으로 처리합니다. 요청에 대한 응답을 Flux<DataBuffer>로 처리하여 스트리밍 형태로 데이터를
   *     받아옵니다.
   */
  public Flux<DataBuffer> postWithBuffer(String url, Object requestBody) {
    Map<String, String> headers = new HashMap<>();
    //        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    headers.put("Accept", MediaType.APPLICATION_JSON_VALUE + ", text/plain, */*");
    headers.put("Accept-Encoding", "gzip, deflate");
    headers.put("Accept-Language", "ko,en;q=0.9,en-US;q=0.8");
    return postWithBuffer(url, requestBody, headers);
  }

  /**
   * POST 요청 (버퍼 처리)
   *
   * @param url 요청할 URI 경로
   * @param requestBody 요청 본문 (JSON)
   * @param headers 요청 헤더
   * @return Flux<DataBuffer> 타입의 응답
   *     <p>이 메소드는 대용량 데이터를 버퍼 단위로 전송받는 방식으로 처리합니다. 요청에 대한 응답을 Flux<DataBuffer>로 처리하여 스트리밍 형태로 데이터를
   *     받아옵니다.
   */
  public Flux<DataBuffer> postWithBuffer(
      String url, Object requestBody, Map<String, String> headers) {
    return webClient
        .post()
        .uri(url)
        .headers(
            httpHeaders -> {
              headers.forEach(httpHeaders::set);
              httpHeaders.set(
                  HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
            }) // 헤더 설정
        .contentType(MediaType.APPLICATION_JSON) // 요청 본문의 타입 설정
        .acceptCharset(StandardCharsets.UTF_8)
        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        .bodyValue(requestBody) // 요청 본문 설정
        .retrieve()
        .bodyToFlux(DataBuffer.class); // 응답 데이터를 버퍼로 변환하여 Flux로 처리
  }

  /**
   * PUT 요청을 처리하는 메서드 (선택적 추가)
   *
   * @param uri 요청할 URI 경로
   * @param request 요청 본문
   * @param headers 요청 헤더
   * @param responseType 응답 타입
   * @param <T> 응답 타입 제네릭
   * @return Mono<T> 타입의 응답
   *     <p>PUT 요청을 보내고 응답을 받아서 Mono로 처리합니다. 리소스를 업데이트할 때 주로 사용됩니다.
   */
  public <T> Mono<T> put(
      String uri, Object request, Map<String, String> headers, Class<T> responseType) {
    return webClient
        .put()
        .uri(uri)
        .headers(httpHeaders -> headers.forEach(httpHeaders::set)) // 요청 헤더 설정
        .body(Mono.just(request), Object.class) // 요청 본문 설정
        .retrieve() // 응답을 가져옴
        .bodyToMono(responseType) // 응답을 Mono로 변환
        .onErrorResume(
            WebClientResponseException.class,
            ex -> {
              // 에러 처리 로직
              log.error("PUT API 호출 중 오류 발생: {}", ex.getMessage());
              return Mono.error(new RuntimeException("PUT 요청 실패"));
            });
  }

  /**
   * DELETE 요청을 처리하는 메서드 (선택적 추가)
   *
   * @param uri 요청할 URI 경로
   * @param headers 요청 헤더
   * @return Mono<Void>
   *     <p>DELETE 요청을 보내고 리소스를 삭제할 때 사용됩니다. 응답은 리소스 삭제 성공 여부만 Mono로 반환합니다.
   */
  public Mono<Void> delete(String uri, Map<String, String> headers) {
    return webClient
        .delete()
        .uri(uri)
        .headers(httpHeaders -> headers.forEach(httpHeaders::set)) // 요청 헤더 설정
        .retrieve() // 응답을 가져옴
        .bodyToMono(Void.class) // 응답을 Mono로 변환 (빈 응답 처리)
        .onErrorResume(
            WebClientResponseException.class,
            ex -> {
              // 에러 처리 로직
              log.error("DELETE API 호출 중 오류 발생: {}", ex.getMessage());
              return Mono.error(new RuntimeException("DELETE 요청 실패"));
            });
  }

  /**
   * 외부 API 호출 (스트리밍 처리)
   *
   * @param url API URL
   * @param requestBody 요청 본문
   * @param headers 요청 헤더
   * @return Flux<String> 타입의 응답 스트림
   */
  public Flux<String> callStreamingApi(
      String url, Object requestBody, Map<String, String> headers) {
    // API 호출
    return webClient
        .post()
        .uri(url)
        .headers(httpHeaders -> headers.forEach(httpHeaders::set))
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .retrieve()
        .bodyToFlux(DataBuffer.class) // 응답 데이터를 DataBuffer로 스트리밍
        .map(this::convertDataBufferToString); // DataBuffer를 문자열로 변환
  }

  /** DataBuffer -> String 변환 */
  private String convertDataBufferToString(DataBuffer buffer) {
    byte[] bytes = new byte[buffer.readableByteCount()];
    buffer.read(bytes);
    return new String(bytes);
  }
}
