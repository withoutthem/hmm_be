/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * LiveChat User Dto
 *
 * @author jhm
 * @since 2025.09.18
 */
@Getter
@Setter
@Builder
public class LiveChatUserDto {

  private String userId;
  private String userName;
  private String displayName;
  private String email;
  private String avatar;
  private String status; // ONLINE, OFFLINE, AWAY, BUSY
  private LocalDateTime lastSeen;
  private String role;
  private boolean isActive;
}
