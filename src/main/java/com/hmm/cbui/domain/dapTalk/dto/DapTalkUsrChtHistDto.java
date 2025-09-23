/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("checkstyle:MemberName")
// 답톡 사용자 대화이력 요청 API용 DTO
public class DapTalkUsrChtHistDto {

  private List<String> listChatbotId; // 챗봇 ID 리스트, 제약사항: 동일테넌트 하위챗봇
  private List<String> listFlowId; // 챗플로우 ID 리스트
  private String userId; // 사용자 ID
  private String sessionId; // 세션 ID
  private String fromDate; // 조회시작일자(챗플로우생성일자) default:하루, 최대7일제약
  private String toDate; // 조회종료일자(챗플로우생성일자) default: now()
  private int offset; // 목록 시작위치 (처음:0)
  private int countLimit; // 조회목록 최대건수
}
