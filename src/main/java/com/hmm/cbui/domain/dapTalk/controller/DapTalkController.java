/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmm.cbui.domain.dapTalk.dto.DapTalkEngineReqDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkEvltMsgDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkReqParametersDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkUsrChtHistDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkWlcmReqDto;
import com.hmm.cbui.domain.dapTalk.service.DapTalkResService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * DapTalk 테스트 controller => test용 파일 삭제 필요
 *
 * @author jym
 * @since 2025.09.03
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/chatflow")
public class DapTalkController {

  private final DapTalkResService dapTalkResService;

  /** WELCOME */
  @PostMapping("/welcome")
  public Mono<ResponseEntity<String>> welcome(@RequestBody DapTalkWlcmReqDto dapTalkWlcmReqDto) {
    return dapTalkResService
        .welcome(dapTalkWlcmReqDto)
        .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json));
  }

  /** Engine */
  @PostMapping("/engine")
  public Mono<ResponseEntity<String>> engine(@RequestBody DapTalkEngineReqDto dapTalkEngineReqDto) {
    return dapTalkResService
        .engine(dapTalkEngineReqDto)
        .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json));
  }

  /** Engine */
  @PostMapping("/event")
  public Mono<ResponseEntity<String>> event(
      @RequestBody DapTalkReqParametersDto dapTalkReqParametersDto) {
    return dapTalkResService
        .event(dapTalkReqParametersDto)
        .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json));
  }

  @PostMapping("/userChatHist")
  public Mono<ResponseEntity<String>> userChatHist(
      @RequestBody DapTalkUsrChtHistDto dapTalkUsrChtHistDto) {
    return dapTalkResService
        .userChatHist(dapTalkUsrChtHistDto)
        .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json));
  }

  @PostMapping("/evaluateMsg")
  public Mono<ResponseEntity<String>> evaluateMsg(
      @RequestBody DapTalkEvltMsgDto dapTalkEvltMsgDto) {
    return dapTalkResService
        .evaluateMsg(dapTalkEvltMsgDto)
        .map(json -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json));
  }
}
