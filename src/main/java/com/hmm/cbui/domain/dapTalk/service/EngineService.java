/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import org.springframework.stereotype.Service;

import com.hmm.cbui.core.annotation.AopIgnore;
import com.hmm.cbui.core.web.WebClientService;

@AopIgnore
@Service
public class EngineService extends AbstractApiService {

  public EngineService(WebClientService webClientService) {
    super(webClientService);
  }

  @Override
  protected String getEndpoint() {
    return "/chatflow/engine";
  }
}
