package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.ChatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRepositoryImplTest {

    @Autowired
    ChatRepository chatRepository;

    @Test
    public void selectAllByUserIdTest() throws Exception {
        List<ChatDto> chatDtoList = chatRepository.selectAllByUserId(1);

        assertEquals(chatDtoList.size(), 2);
    }

    @Test
    public void selectAllByUuidTest() throws Exception {
        List<ChatDto> chatDtoList = chatRepository.selectAllByUuid("daf1c721-54ef-4b40-970f-12705058ab5f");

        assertEquals(chatDtoList.size(), 2);
    }

    @Test
    @Transactional
    public void insertTest() throws Exception {
        ChatDto chatDto = new ChatDto(1, "df58136a-fasd5618", "user", "테스트 메시지입니다.");

        int insertCnt = chatRepository.insert(chatDto);

        assertEquals(insertCnt, 1);
    }
}