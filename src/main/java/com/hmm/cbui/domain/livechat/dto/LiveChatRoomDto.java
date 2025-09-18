/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * LiveChat Room Dto
 *
 * @author jhm
 * @since 2025.09.18
 */
@Getter
@Setter
@Builder
public class LiveChatRoomDto {

  private String roomId;
  private String roomName;
  private String description;
  private String status; // ACTIVE, INACTIVE, MAINTENANCE
  private String createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<String> participants;
  private int participantCount;
  private String lastMessage;
  private LocalDateTime lastMessageAt;
}
