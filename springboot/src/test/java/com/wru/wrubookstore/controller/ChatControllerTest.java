package com.wru.wrubookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wru.wrubookstore.dto.ChatDto;
import com.wru.wrubookstore.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ChatService chatService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("채팅 내역 조회 테스트")
    public void getChatHistoryTest() throws Exception {
        // given
        Integer userId = 3;

        List<ChatDto> list = new ArrayList<>();
        list.add(new ChatDto(userId, "asdf", "user", "안녕"));

        BDDMockito.given(chatService.selectAllByUserId(userId)).willReturn(list);


        // when,then
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/history")
                .param("uuid", "asdf")
                .sessionAttr("userId", userId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sender").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("안녕"));
    }

    @Test
    @DisplayName("채팅 전송 및 답변 테스트")
    @Transactional
    public void sendMessageTest() throws Exception {
        // given
        Integer userId = 4;
        ChatDto requestBody = new ChatDto(userId, "Asdf", "user", "안녕하세요");

        // when,then
        mockMvc.perform(MockMvcRequestBuilders.post("/chat/send")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestBody)))
                .andDo(MockMvcResultHandlers.print());
    }
}