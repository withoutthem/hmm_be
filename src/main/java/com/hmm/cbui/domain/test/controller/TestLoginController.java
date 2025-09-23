/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
public class TestLoginController {

  @GetMapping("/loginpass")
  public Mono<ServerResponse> login(ServerRequest request) {
    return request
        .session()
        .flatMap(
            webSession -> {
              webSession.getAttributes().put("user", "1234");
              return ServerResponse.ok().bodyValue("로그인 성공");
            });
  }
}
