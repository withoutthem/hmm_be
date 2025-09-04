/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HmmCBApiController {
  @PostMapping("/email/send")
  public Mono<String> hello() {
    return Mono.just("Hello, WebFlux!");
  }

  @PostMapping("/notice/birthDay")
  public Mono<String> noticeBirthDay() {
    return Mono.just("Happy  Birthday!");
  }

  @GetMapping("/numbers")
  public Flux<Integer> numbers() {
    return Flux.range(1, 10);
  }
}
