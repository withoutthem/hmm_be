/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DapTalkService {

  /**
   * 챗봇 목록 조회
   *
   * @param dapTalkChtbCatlgu
   * @return PageResponse<DapTalkChtbCatlgu>
   */
  //    public Response<DapTalkChtbCatlgu> getListChtbCatlgu2(DapTalkChtbCatlgu dapTalkChtbCatlgu) {
  //        ObjectMapper objectMapper = new ObjectMapper();
  //        Response<DapTalkChtbCatlgu> response = new Response<>();
  //
  //        // JSON 파일 경로
  //        String filePath = "frontend/test_chatbot_list2.json";
  //        String tenantId = dapTalkChtbCatlgu.getTenantId();
  //
  //        try {
  //
  //            // 파일을 읽어 JsonNode로 변환
  //            JsonNode rootNode = objectMapper.readTree(new File(filePath));
  //            // 'result' 배열을 읽음
  //            JsonNode responseSetArray = rootNode.get("responseSet");
  //
  //            if (responseSetArray != null && responseSetArray.isArray()) {
  //                Iterator<JsonNode> elements = responseSetArray.elements();
  //                while (elements.hasNext()) {
  //                    JsonNode tenantNode = elements.next();
  //
  //                    // tenantId를 확인하여 필터링
  //                    String currentTenantId = tenantNode.get("tenant_id").asText();
  //                    if (currentTenantId.equals(tenantId)) {
  //                        // 매칭되는 tenantId가 있으면, DapTalkChtbCatlgu에 매핑
  //                        DapTalkChtbCatlgu matchedTenant = new DapTalkChtbCatlgu();
  //                        matchedTenant.setTenantId(currentTenantId);
  //                        matchedTenant.setTenantName(tenantNode.get("tenant_nm").asText());
  //
  //                        // 'result' 배열을 읽어 List<Result>로 변환
  //                        List<DapTalkChtbCatlgu.Result> chatbotResults = new ArrayList<>();
  //                        JsonNode resultArray = tenantNode.get("result");
  //                        if (resultArray != null && resultArray.isArray()) {
  //                            for (JsonNode chatbotNode : resultArray) {
  //                                DapTalkChtbCatlgu.Result chatbot = new
  // DapTalkChtbCatlgu.Result();
  //                                chatbot.setChatbot_id(chatbotNode.get("chatbot_id").asText());
  //
  // chatbot.setChatbot_code(chatbotNode.get("chatbot_code").asText());
  //
  // chatbot.setChatbot_name(chatbotNode.get("chatbot_name").asText());
  //                                chatbot.setImage_url(chatbotNode.get("image_url").asText());
  //                                chatbot.setRole(chatbotNode.get("role").asText());
  //                                chatbot.setType(chatbotNode.get("type").asText());
  //
  // chatbot.setChatbot_state(chatbotNode.get("chatbot_state").asText());
  //
  // chatbot.setDescription(chatbotNode.get("description").asText(null));
  //                                chatbot.setCreator(chatbotNode.get("creator").asText());
  //                                chatbot.setCreate_date(chatbotNode.get("create_date").asText());
  //                                chatbot.setUpdate_date(chatbotNode.get("update_date").asText());
  //                                chatbot.setIs_delete(chatbotNode.get("is_delete").asText());
  //
  // chatbot.setService_key(chatbotNode.get("service_key").asText(null));
  //                                chatbot.setExpand_data(chatbotNode.get("expand_data").asText());
  //
  //                                chatbotResults.add(chatbot);
  //                            }
  //                        }
  //                        matchedTenant.setResult(chatbotResults);
  //
  //                        // 데이터를 response 객체에 담기
  //                        response.setData(matchedTenant);
  //                        response.setMessage("Success");
  //                        response.setError(false);
  //                        return response;
  //                    }
  //                }
  //            }
  //
  //            // tenantId를 찾지 못한 경우
  //            response.setMessage("Tenant ID " + tenantId + " not found");
  //            response.setError(true);
  //            response.setErrorCd("404");
  //
  //        } catch (IOException e) {
  //            response.setMessage("JSON 파일을 읽는 중 오류 발생");
  //            response.setError(true);
  //            response.setErrorCd("500");
  //        }
  //
  //        return response;
  //    }

}
