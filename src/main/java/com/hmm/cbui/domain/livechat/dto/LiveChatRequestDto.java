/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * LiveChat Request Dto
 *
 * @author jhm
 * @since 2025.09.18
 */
@Getter
@Builder
public class LiveChatRequestDto {
  @NotBlank(message = "Room ID는 필수잆니다")
  private String roomId;

  @NotBlank(message = "발신자는 필수입니다")
  private String sender;

  @NotBlank(message = "메시지 내용은 필수입니다 ")
  private String content;

  private String messageType; // TEXT, IMAGE, FILE, SYSTEM
  private String replyTo; // 답장 대상 메시지 ID
  private String metadata; // 추가 메타데이터 (JSON 문자열)
}
