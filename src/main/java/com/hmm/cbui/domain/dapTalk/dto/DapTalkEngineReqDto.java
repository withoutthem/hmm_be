/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("checkstyle:MemberName")
// 답톡 엔진 요청API용 DTO
public class DapTalkEngineReqDto {

  private String version; // 버전
  private String chatbot_id; // 챗봇ID
  private String input_sentence; // 사용자 대화 문장
  private String user_id; // 사용자ID
  private String session_id; // 대화 세션 ID
  private String ins_id; // 인스턴스 ID
  private String intent_id; // 인텐트 ID
  private String node_id; // 노드 ID
  private String param_id; // 파라미터 ID
  private String chatflow_id; // 챗플로우 ID
  private DapTalkReqParametersDto parameters; // 파라미터
}
