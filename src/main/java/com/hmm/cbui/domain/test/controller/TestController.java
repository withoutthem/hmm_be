/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.test.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class TestController {

  /** 단순 REST 확인용 */
  @GetMapping("/rest")
  public Mono<ResponseEntity<String>> rest() {
    return Mono.just(ResponseEntity.ok("HTTP REST API is working!"));
  }

  /** SSE 테스트용: 1초마다 tick 전송-test */
  @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<String>> sse() {
    return Flux.interval(Duration.ofSeconds(1))
        .map(
            i ->
                ServerSentEvent.<String>builder()
                    .id(Long.toString(i))
                    .event("tick")
                    .data("hello-" + i)
                    .build());
  }
}
