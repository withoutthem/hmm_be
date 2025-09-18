/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * LiveChat Auth Dto
 *
 * @author jhm
 * @since 2025.09.18
 */
@Getter
@Setter
@Builder
@ToString(callSuper = true)
@Schema(name = "라이브챗 Auth 정보 DTO")
public class LiveChatAuthDto {
  // request
  @Schema(description = "HMM에서 발급한 assertion 토큰")
  private String assertionToken;

  @Schema(description = "HMM 통합아이디")
  private String userId;

  // response
  @Schema(description = "토큰 유효 여부(HMM API와 연계)")
  private Boolean isValid;

  @Schema(description = "사용자 이름")
  private String userNm;

  @Schema(description = "접속중인 사용자의 라이브챗 토큰")
  private String liveChatToken;

  @Schema(description = "접속중인 사용자의 챗봇 이메일")
  private String cbtEmail;
}
