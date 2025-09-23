/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.hmm.cbui.domain.dapTalk.dto.DapTalkEngineReqDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkEvltMsgDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkReqParametersDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkUsrChtHistDto;
import com.hmm.cbui.domain.dapTalk.dto.DapTalkWlcmReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DapTalkResService {
  private static final String RESULT = "result";
  private static final String EXTERNAL_API_BASE_URL = "https://ddapplfm.hmm21.com/";

  private final WebClient webClient;
  @Autowired private ObjectMapper objectMapper;

  /** Welcome */
  public Mono<String> welcome(DapTalkWlcmReqDto dapTalkWlcmReqDto) {
    String uri = "chatflow/welcome.do";
    return requestDaptalk(uri, dapTalkWlcmReqDto);
  }

  /** Engine */
  public Mono<String> engine(DapTalkEngineReqDto dapTalkEngineReqDto) {
    String uri = "chatflow/engine.do";
    return requestDaptalk(uri, dapTalkEngineReqDto);
  }

  /** EventFlow 호출 */
  public Mono<String> event(DapTalkReqParametersDto dapTalkReqParametersDto) {
    String uri = "chatflow/v2/api/eventFlow.do";
    return requestDaptalk(uri, dapTalkReqParametersDto);
  }

  /** EventFlow 호출 */
  public Mono<String> userChatHist(DapTalkUsrChtHistDto dapTalkUsrChtHistDto) {
    String uri = "chatflow/userChatHist.do";
    return requestDaptalk(uri, dapTalkUsrChtHistDto);
  }

  /** 사용자 평가 호출 */
  public Mono<String> evaluateMsg(DapTalkEvltMsgDto dapTalkEvltMsgDto) {
    String uri = "chatflow/evaluateMsg.do";
    return requestDaptalk(uri, dapTalkEvltMsgDto);
  }

  /**
   * 임시로 json 파일 처리
   *
   * @param input_sentence
   * @return String
   */
  @SuppressWarnings("checkstyle:MemberName")
  public String extractJson(String input_sentence) {

    // JSON 파일 경로
    String filePath = "frontend/test.json";

    // JSON 파일 읽기
    try {
      // ObjectMapper 객체 생성
      ObjectMapper objectMapper = new ObjectMapper();

      // 파일을 읽어 JsonNode로 변환
      JsonNode rootNode = objectMapper.readTree(new File(filePath));

      // responseSet 내의 result를 가져옴
      JsonNode responseSet = rootNode.get("responseSet");
      JsonNode resultNode = responseSet.get(RESULT).get(RESULT);

      // 결과를 저장할 새 JsonNode 객체 생성
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode newResult = mapper.createObjectNode();

      // result 배열을 순회하여 input_sentence와 일치하는 항목 찾기
      for (JsonNode resultItem : resultNode) {
        String inputSentenceValue = resultItem.get("input_sentence").asText();

        // input_sentence가 일치하면 해당 result를 새 JsonNode에 추가
        if (inputSentenceValue.equals(input_sentence)) {
          // 새 responseSet에 필요한 데이터만 추가
          ObjectNode responseSetNode = mapper.createObjectNode();
          responseSetNode.put("code", responseSet.get("code").asText());

          // result 내부 구조 생성
          ObjectNode resultDataNode = mapper.createObjectNode();
          resultDataNode.put("version", responseSet.get(RESULT).get("version").asText());
          resultDataNode.put("chatbot_id", responseSet.get(RESULT).get("chatbot_id").asText());
          resultDataNode.put("user_id", responseSet.get(RESULT).get("user_id").asText());
          resultDataNode.put("ins_id", responseSet.get(RESULT).get("ins_id").asText());
          resultDataNode.put("session_id", responseSet.get(RESULT).get("session_id").asText());

          // result 항목 추가
          resultDataNode.set(RESULT, mapper.createArrayNode().add(resultItem));
          // resultStatus를 추가 (session_id와 같은 레벨)
          JsonNode resultStatusNode = responseSet.get(RESULT).get("resultStatus");
          if (resultStatusNode != null) {
            resultDataNode.set("resultStatus", resultStatusNode);
          }

          responseSetNode.set(RESULT, resultDataNode);

          // 새로운 JSON 객체에 responseSet 추가
          newResult.set("responseSet", responseSetNode);

          // JSON 포맷 문자열로 변환하여 반환
          return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newResult);
        }
      }

    } catch (IOException e) {
      log.error("{}", e.getMessage());
      //            e.printStackTrace();
    }

    // 만약 해당 input_sentence가 없을 경우 빈 문자열 반환
    return "{}";
  }

  public Mono<String> requestDaptalk(String uri, Object object) {
    return webClient
        .post()
        .uri(EXTERNAL_API_BASE_URL + uri)
        .bodyValue(object)
        .retrieve()
        .bodyToMono(String.class)
        .map(
            json -> {
              try {
                JsonNode root = objectMapper.readTree(json).get("responseSet").get("result");
                return objectMapper.writeValueAsString(root);
              } catch (JsonProcessingException e) {
                log.error("Json is not valid : {}", e.getMessage());
              }
              return json;
            })
        .doOnNext(response -> log.info("Response Body: {}", response));
  }
}
