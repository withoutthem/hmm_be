/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.livechat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.hmm.cbui.domain.livechat.dto.LiveChatRequestDto;
import com.hmm.cbui.domain.livechat.dto.LiveChatResponseDto;
import com.hmm.cbui.domain.livechat.dto.LiveChatRoomDto;
import com.hmm.cbui.domain.livechat.dto.LiveChatUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * LiveChat External API 라이브챗관련 Mocking 모듈 서비스 ( 실제 외부 API 연동시 구현 )
 *
 * @author jhm
 * @since 2025.09.18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalLiveChatApiService {

  private final WebClient webClient;

  private static final String EXTERNAL_API_BASE_URL = "https://external-livechat-api.com/api/v1";

  /** 외부 API로 메시지 전송 */
  public Mono<LiveChatResponseDto> sendMessage(LiveChatRequestDto request) {
    log.info(
        "외부 API에서 메시지 전송 요청 : roomId = {} , sender = {}", request.getRoomId(), request.getSender());
    // 실제 외부API 호출영역
    return Mono.just(mockSendMessageResponse(request));
  }

  /** 외부 API로 메시지 목록 조회 */
  public Mono<List<LiveChatResponseDto>> getMessage(String roomId, int limit) {
    log.info("외부 API에서 메시지 목록 조회 : roomId = {}, limit = {}", roomId, limit);
    // 실제 외부API 호출영역
    return Mono.just(mockGetMessageResponse(roomId, limit));
  }

  /** 외부 API로 채팅방 정보 조회 */
  public Mono<LiveChatRoomDto> getRoomInfo(String roomId) {
    log.info("외부 API에서 채팅방 정보 조회 : roomId = {}", roomId);
    // 실제 외부API 호출영역
    return Mono.just(mockGetRoomInfoResponse(roomId));
  }

  /** 외부 API로 사용자 정보 조회 */
  public Mono<LiveChatUserDto> getUserInfo(String userId) {
    log.info("외부 API에서 사용자 정보 조회 : userId = {}", userId);
    // 실제 외부API 호출영역
    return Mono.just(mockGetUserInfoResponse(userId));
  }

  /** 외부 API로 채팅방 생성 요청 */
  public Mono<LiveChatRoomDto> createRoom(LiveChatRoomDto roomRequest) {
    log.info("외부 API로 채팅방 생성 요청 : roomName = {}", roomRequest.getRoomName());
    // 실제 외부API 호출영역
    return Mono.just(mockCreateRoomResponse(roomRequest));
  }

  /** 모킹 응답 매서드 영역 (추후 실제 외부 API 연결 ) --> START */
  private LiveChatResponseDto mockSendMessageResponse(LiveChatRequestDto request) {
    return LiveChatResponseDto.builder()
        .messageId(UUID.randomUUID().toString())
        .roomId(request.getRoomId())
        .sender(request.getSender())
        .content(request.getContent())
        .messageType(request.getMessageType())
        .timestamp(LocalDateTime.now())
        .status("SENT")
        .replyTo(request.getReplyTo())
        .metadata(request.getMetadata())
        .isEdited(false)
        .build();
  }

  private List<LiveChatResponseDto> mockGetMessageResponse(String roomId, int limit) {
    return List.of(
        LiveChatResponseDto.builder()
            .messageId(UUID.randomUUID().toString())
            .roomId(roomId)
            .sender("user1")
            .content("안녕하세요! 라이브챗 테스트 입니다.")
            .messageType("TEXT")
            .timestamp(LocalDateTime.now())
            .status("READ")
            .isEdited(false)
            .build(),
        LiveChatResponseDto.builder()
            .messageId(UUID.randomUUID().toString())
            .roomId(roomId)
            .sender("user2")
            .content("네 안녕하세요! 잘 작동하고 있습니다.")
            .messageType("TEXT")
            .timestamp(LocalDateTime.now())
            .status("READ")
            .isEdited(false)
            .build());
  }

  private LiveChatRoomDto mockGetRoomInfoResponse(String roomId) {
    return LiveChatRoomDto.builder()
        .roomId(roomId)
        .roomName("테스트 채팅방")
        .description("라이브챗 API 테스트용 채팅방입니다.")
        .status("ACTIVE")
        .createdBy("admin")
        .createdAt(LocalDateTime.now().minusDays(1))
        .updatedAt(LocalDateTime.now().minusMinutes(10))
        .participants(List.of("user1", "user2", "user3"))
        .participantCount(3)
        .lastMessage("네 안녕하세요! 채팅방이 잘 작동하고 있습니다 ")
        .lastMessageAt(LocalDateTime.now().minusMinutes(13))
        .build();
  }

  private LiveChatUserDto mockGetUserInfoResponse(String userId) {
    return LiveChatUserDto.builder()
        .userId(userId)
        .userName("userId " + userId)
        .displayName("사용자 " + userId)
        .email("user" + userId + "@naver.com")
        .avatar("http://naver.com/avatar/" + userId + ".jpg")
        .lastSeen(LocalDateTime.now().minusMinutes(1))
        .role("USER")
        .isActive(true)
        .build();
  }

  private LiveChatRoomDto mockCreateRoomResponse(LiveChatRoomDto roomRequest) {
    return LiveChatRoomDto.builder()
        .roomId(UUID.randomUUID().toString())
        .roomName(roomRequest.getRoomName())
        .description(roomRequest.getDescription())
        .status("ACTIVE")
        .createdBy(roomRequest.getCreatedBy())
        .updatedAt(roomRequest.getUpdatedAt())
        .participants(List.of(roomRequest.getCreatedBy()))
        .participantCount(1)
        .build();
  }
  /** 모킹 응답 매서드 영역 (추후 실제 외부 API 연결 ) --> END */
}
