/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("checkstyle:MemberName")
// 대화평가 API용 DTO
public class DapTalkEvltMsgDto {
  private String chatbot_id;
  private String ins_id;
  private String evaluate_flag;
  private String evaluate_message;
}
