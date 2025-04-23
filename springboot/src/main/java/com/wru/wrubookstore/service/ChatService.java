package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.ChatDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatService {

    // 로그인 사용자 채팅 내역 출력
    List<ChatDto> selectAllByUserId(Integer userId) throws Exception;
    // 비로그인 사용자 채팅 내역 출력
    List<ChatDto> selectAllByUuid(String uuid) throws Exception;
    // 채팅 저장
    int insert(ChatDto chatDto) throws Exception;

    /* 비즈니스 로직 */
    // Fast API와 통신하여 LLM 답변 가져오기
    Mono<ChatDto> getLLMResponse(ChatDto chatDto) throws Exception;
}
