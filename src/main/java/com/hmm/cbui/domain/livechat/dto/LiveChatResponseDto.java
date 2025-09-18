/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * LiveChat Response Dto
 *
 * @author jhm
 * @since 2025.09.18
 */
@Getter
@Setter
@Builder
public class LiveChatResponseDto {

  private String messageId;
  private String roomId;
  private String sender;
  private String content;
  private String messageType;
  private LocalDateTime timestamp;
  private String replyTo;
  private String status;
  private String metadata;
  private Boolean isEdited;
  private LocalDateTime editedAt;
}
