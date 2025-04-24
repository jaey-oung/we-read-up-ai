package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.ChatDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.ChatMapper.";
    private final SqlSessionTemplate session;

    public ChatRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    // 로그인 사용자 채팅 내역 출력
    @Override
    public List<ChatDto> selectAllByUserId(Integer userId) throws Exception {
        return session.selectList(namespace + "selectAllByUserId", userId);
    }

    // 비로그인 사용자 채팅 내역 출력
    @Override
    public List<ChatDto> selectAllByUuid(String uuid) throws Exception {
        return session.selectList(namespace + "selectAllByUuid", uuid);
    }

    // 채팅 저장
    @Override
    public int insert(ChatDto chatDto) throws Exception {
        return session.insert(namespace + "insert", chatDto);
    }
}
