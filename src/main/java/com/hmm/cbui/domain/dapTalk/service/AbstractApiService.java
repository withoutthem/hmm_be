/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.dapTalk.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hmm.cbui.core.web.WebClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractApiService {

  private final WebClientService webClientService;

  @Value("${daptalk.url}")
  private String base_url;

  @Value("{daptalk.botboongiurl}")
  private String botboongiurl;

  protected abstract String getEndpoint();

  public ResponseEntity<String> callApi(Object requestBody) {
    String fullUrl = base_url + getEndpoint();

    try {
      // DataBuffer로 처리
      String responseBody = webClientService.post(fullUrl, requestBody, String.class).block();
      return ResponseEntity.ok(responseBody);
    } catch (WebClientRequestException e) {
      log.error("DapTalk API 호출 중 요청 오류 발생: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 오류 발생: " + e.getMessage());
    } catch (WebClientResponseException e) {
      log.error(
          "DapTalk API 호출 중 응답 오류 발생 - 상태 코드: {}, 응답 본문: {}",
          e.getRawStatusCode(),
          e.getResponseBodyAsString());
      return ResponseEntity.status(e.getRawStatusCode())
          .body(
              "응답 오류 발생 - 상태 코드: "
                  + e.getRawStatusCode()
                  + ", 응답 본문: "
                  + e.getResponseBodyAsString());
    } catch (DataBufferLimitException e) {
      log.error("응답 처리 중 DataBuffer 제한 초과 오류 발생: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
          .body("DataBuffer 제한 초과 오류 발생: " + e.getMessage());
    } catch (Exception e) {
      log.error("DapTalk API 호출 중 알 수 없는 오류 발생: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("알 수 없는 오류 발생: " + e.getMessage());
    }
  }

  // 기본 GET 방식 요청 처리 메서드
  public ResponseEntity<String> callGetApi(Map<String, String> queryParams) {
    try {
      // 파라미터 인코딩
      String encodedParams = encodeParams(queryParams); // queryParams 맵을 인코딩하여 URL 쿼리 스트링으로 변환
      String fullUrl = botboongiurl + getEndpoint() + "?" + encodedParams; // 기본 URL에 인코딩된 파라미터 추가

      // GET 요청
      String responseBody =
          webClientService
              .get(fullUrl, String.class)
              .block(); // WebClient로 GET 요청을 보내고 결과를 동기적으로 반환
      return ResponseEntity.ok(responseBody);

    } catch (WebClientRequestException e) {
      log.error("Get API 호출 중 요청 오류 발생: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 오류 발생: " + e.getMessage());
    } catch (WebClientResponseException e) {
      log.error(
          "Get API 호출 중 응답 오류 발생 - 상태 코드: {}, 응답 본문: {}",
          e.getRawStatusCode(),
          e.getResponseBodyAsString());
      return ResponseEntity.status(e.getRawStatusCode())
          .body(
              "응답 오류 발생 - 상태 코드: "
                  + e.getRawStatusCode()
                  + ", 응답 본문: "
                  + e.getResponseBodyAsString());
    } catch (Exception e) {
      log.error("Get API 호출 중 알 수 없는 오류 발생: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("알 수 없는 오류 발생: " + e.getMessage());
    }
  }

  // 쿼리 파라미터 인코딩 메서드
  private String encodeParams(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder encodedParams = new StringBuilder();
    for (Map.Entry<String, String> ignored : params.entrySet()) {
      if (!encodedParams.isEmpty()) {
        encodedParams.append("&");
      }
      //            encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      encodedParams.append("=");
      //            encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return encodedParams.toString();
  }
}
