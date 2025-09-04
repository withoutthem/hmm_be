/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * 챗봇 목록 조회 Dto
 *
 * @author jym
 * @since 2025.09.03
 */
@Getter
@Setter
public class DapTalkChtbCatlgu {

  private String tenantId; // 테넌트ID
  private String tenantName; // 테넌트명 - 임시 => 추후 삭제
  private List<Result> result; // 결과 - 임시 => 추후 삭제

  // 임시 => 추후 삭제
  @Getter
  @Setter
  @SuppressWarnings("checkstyle:MemberName")
  public static class Result {
    private String chatbot_id; // 챗봇ID
    private String chatbot_code; // 챗봇 코드
    private String chatbot_name; // 챗봇명
    private String image_url; // 챗봇 이미지 url
    private String role; // 챗봇 역할
    private String type; // 챗봇 유형
    private String chatbot_state; // 챗봇 상태
    private String description; // 설명
    private String creator; // 챗봇 생성자
    private String create_date; // 챗봇 생성일
    private String update_date; // 챗봇 변경일
    private String is_delete; // 삭제 여부
    private String service_key; // 서비스키

    // 확장 데이터가 JSON으로 인코딩되어 있기 때문에 문자열로 받고 파싱이 필요함
    private String expand_data; // 일단 String으로 받음

    // 확장 데이터를 파싱하는 메서드
    public Map<String, Object> getExpandDataAsMap() throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(this.expand_data, Map.class);
    }
  }

  //    private String chatbot_id;      // 챗봇ID
  //    private String chatbot_code;    // 챗봇 코드
  //    private String chatbot_name;    // 챗봇명
  //    private String image_url;       // 챗봇 이미지 url
  //    private String role;            // 챗봇 역할
  //    private String type;            // 챗봇 유형
  //    private String chatbot_state;   // 챗봇 상태
  //    private String description;     // 설명
  //    private String creator;         // 챗봇 생성자
  //    private String create_date;     // 챗봇 생성일
  //    private String update_date;     // 챗봇 변경일
  //    private String is_delete;       // 삭제 여부
  //    private String service_key;     // 서비스키
  //
  //    // 확장 데이터가 JSON으로 인코딩되어 있기 때문에 문자열로 받고 파싱이 필요함
  //    private String expand_data;     // 일단 String으로 받음
  //
  //    // 확장 데이터를 파싱하는 메서드
  //    public Map<String, Object> getExpandDataAsMap() throws JsonProcessingException {
  //        ObjectMapper objectMapper = new ObjectMapper();
  //        return objectMapper.readValue(this.expand_data, Map.class);
  //    }

}
