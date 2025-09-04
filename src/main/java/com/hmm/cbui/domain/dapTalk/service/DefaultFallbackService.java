/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import org.springframework.stereotype.Service;

import com.hmm.cbui.core.annotation.AopIgnore;
import com.hmm.cbui.core.web.WebClientService;

import lombok.extern.slf4j.Slf4j;

@AopIgnore
@Service
@Slf4j
public class DefaultFallbackService extends AbstractApiService {

  public DefaultFallbackService(WebClientService webClientService) {
    super(webClientService);
  }

  @Override
  protected String getEndpoint() {
    // 기본 Fallback 엔드포인트 설정
    return "/botboongi/search/";
  }
}
