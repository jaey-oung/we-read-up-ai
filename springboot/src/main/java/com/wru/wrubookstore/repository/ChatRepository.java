package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.ChatDto;

import java.util.List;

public interface ChatRepository {

    /* SELECT */
    // 로그인 사용자 채팅 내역 출력
    List<ChatDto> selectAllByUserId(Integer userId) throws Exception;

    // 비로그인 사용자 채팅 내역 출력
    List<ChatDto> selectAllByUuid(String uuid) throws Exception;

    /* INSERT */
    // 채팅 저장
    int insert(ChatDto chatDto) throws Exception;
}
