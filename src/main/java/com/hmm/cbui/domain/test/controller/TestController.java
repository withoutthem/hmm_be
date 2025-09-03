/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmm.cbui.domain.livechat.dto.LiveChatMessageDto;
import com.hmm.cbui.domain.livechat.service.LiveChatService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  private final LiveChatService liveChatService; // 주입 대상을 LiveChatService로 변경

  @GetMapping("/rest")
  public Mono<ResponseEntity<String>> testRestApi() {
    return Mono.just(ResponseEntity.ok("HTTP REST API is working!"));
  }

  @PostMapping("/broadcast/{roomId}")
  public Mono<ResponseEntity<Void>> testWebSocketBroadcast(
      @PathVariable String roomId, @RequestBody LiveChatMessageDto message) {
    // LiveChatService의 publish 메소드를 호출
    liveChatService.publish(roomId, message);
    return Mono.just(ResponseEntity.ok().build());
  }
}
