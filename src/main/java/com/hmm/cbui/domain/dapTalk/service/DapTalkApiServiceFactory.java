/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import org.springframework.stereotype.Component;

import com.hmm.cbui.core.annotation.AopIgnore;
import com.hmm.cbui.core.enums.MsgType;

import lombok.RequiredArgsConstructor;

@AopIgnore
@RequiredArgsConstructor
@Component
public class DapTalkApiServiceFactory {

  private final WlcmService wlcmService;
  private final EngineService engineService;
  private final DefaultFallbackService defaultFallbackService;

  // 요청할 메서드명에 따라 적절한 구체 클래스를 반환
  public AbstractApiService getService(MsgType msgType) {
    return switch (msgType) {
      case WELCOME -> wlcmService;
      case ENGINE -> engineService;
      case DF -> defaultFallbackService;
      default -> throw new IllegalArgumentException("Unknown message type: " + msgType.toString());
    };
  }
}
