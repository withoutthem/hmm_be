/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("checkstyle:MemberName")
public class DapTalkWlcmReqDto {

  private String chatbot_id; // 챗봇ID
  private String user_id; // 사용자ID
  private DapTalkReqParametersDto parameters; // 파라미터
}
