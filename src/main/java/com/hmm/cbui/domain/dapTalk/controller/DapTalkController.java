/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hmm.cbui.domain.dapTalk.dto.DapTalkEngineReqDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkWlcmReqDto;
import com.hmm.cbui.domain.dapTalk.service.DapTalkResService;

import lombok.RequiredArgsConstructor;

/**
 * DapTalk 테스트 controller => test용 파일 삭제 필요
 *
 * @author jym
 * @since 2025.09.03
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/chatflow")
public class DapTalkController {

  private final DapTalkResService dapTalkResService;

  /** WELCOME */
  @PostMapping("/welcome")
  public ResponseEntity<String> welcome(@RequestBody DapTalkWlcmReqDto dapTalkWlcmReqDto) {
    return new ResponseEntity<>(dapTalkResService.welcome(dapTalkWlcmReqDto), HttpStatus.OK);
  }

  /** Engine */
  @PostMapping("/engine")
  public ResponseEntity<String> engine(@RequestBody DapTalkEngineReqDto dapTalkEngineReqDto) {
    return new ResponseEntity<>(dapTalkResService.engine(dapTalkEngineReqDto), HttpStatus.OK);
  }
}
