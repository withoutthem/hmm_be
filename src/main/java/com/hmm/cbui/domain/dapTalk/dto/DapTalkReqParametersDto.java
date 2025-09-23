/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("checkstyle:MemberName")
// 답톡 엔진 API 요청시 parameters 변수에 적용할수있는 DTO
public class DapTalkReqParametersDto {

  private String user_name; // 고객 이름
  private String g_session_id; // G세션ID
  private String channel_id; // 채널 구분
  private String login_id; // 회원ID
  private String web_user_id; // login_id 값을 받아온다.
  private String eventChatFlowId; // 이벤트 챗플로우ID
  private String custIdnfr; // 고객식별자
  private String bzwkDsticCd; // 업무구분 코드
  private String categorySerno; // 카테고리 일련번호
  private String eventName; // 이벤트구분
  private String sbjctCd; // 과목코드
  private String sendSerno; // 발송일련번호
  private String bannerEventId; // 이벤트ID
  private String chatbotReservedMsg; // 챗봇예약메시지
  private String statSaveOption; // 통계정보저장옵션
  private String brnc_use_yn; // 봇전환여부
  private String brnc_find_yn; // 봇분기실행여부
  private Map<String, Object> addParameters;
}
