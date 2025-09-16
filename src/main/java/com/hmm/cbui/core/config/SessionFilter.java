/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.config;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class SessionFilter implements WebFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return exchange.getSession()
            .flatMap(session -> {
              // 세션에서 값 확인 또는 설정
              String userId = (String) session.getAttributes().get("userId");
              if (userId == null) {
                session.getAttributes().put("userId", "guest");
              }
              return chain.filter(exchange);
            });
  }

}
