/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.aspect;

import java.net.URI;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import com.hmm.cbui.core.annotation.WebSocketClient;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

@Aspect
@Component
public class WebSocketMessageAspect {

  private static final int LIMIT = 2;
  // Reactor Netty WebSocketClient

  @Around("@annotation(wsClient)")
  public Object handleWebSocketMessage(ProceedingJoinPoint pjp, WebSocketClient wsClient)
      throws Throwable {
    // 1) 비즈니스 로직 수행: payload 생성
    String payload = (String) pjp.proceed();

    // 2) HttpClient에 헤더 주입 (Token, CORS 등)
    HttpClient nettyClient =
        HttpClient.create()
            .headers(
                h -> {
                  for (String header : wsClient.headers()) {
                    String[] kv = header.split("=", LIMIT);
                    if (kv.length == LIMIT) {
                      h.add(kv[0], kv[1]);
                    }
                  }
                });

    // 3) ReactorNettyWebSocketClient 생성
    ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient(nettyClient);

    // 4) 모드에 따른 처리
    URI uri = URI.create(wsClient.url());
    return switch (wsClient.mode()) {
      case SEND -> {
        // 동기 전송 (블로킹 없이 join 한 번만 수행)
        client
            .execute(uri, session -> session.send(Mono.just(session.textMessage(payload))).then())
            .toFuture()
            .join();
        yield null;
      }
      case SEND_ASYNC ->
      // 비동기 전송: Mono<Void> 리턴
      client.execute(uri, this::sendPayload).publishOn(Schedulers.parallel());
      case CONNECT ->
      // 연결만 수행하고 세션 관리 로직 필요 시 pjp.proceed()
      client.execute(uri, session -> Mono.empty());
      case CLOSE ->
      // 별도 CLOSE 처리 로직 (예: 세션 캐싱 후 종료)
      client.execute(uri, session -> session.close().then());
    };
  }

  private Mono<Void> sendPayload(WebSocketSession session) {
    // 실제 메시지 전송 흐름 (session 텍스트 메시지 발송 후 자동 종료)
    return session.send(Mono.just(session.textMessage(session.getId()))).then();
  }
}
