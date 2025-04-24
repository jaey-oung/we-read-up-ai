package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.ChatDto;
import com.wru.wrubookstore.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatDto>> getChatHistory(@SessionAttribute(required = false) Integer userId,
                                                        @RequestParam String uuid) {
        List<ChatDto> chatDtoList = null;

        try {
            // 비로그인 시 uuid를 통해 채팅 내역 조회
            if (userId == null) {
                chatDtoList = chatService.selectAllByUuid(uuid);
            }
            // 로그인 시 userId를 통해 채팅 내용 조회
            else {
                chatDtoList = chatService.selectAllByUserId(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(chatDtoList);
    }

    @PostMapping("/send")
    public ResponseEntity<Mono<ChatDto>> sendMessage(@SessionAttribute(required = false) Integer userId, @RequestBody ChatDto chatDto) {
        Mono<ChatDto> llmResponse = null;
        chatDto.setUserId(userId); // 채팅에 사용자 id 설정

        System.out.println("chatDto = " + chatDto);

        try {
            chatService.insert(chatDto); // 사용자 채팅 DB에 저장
            llmResponse = chatService.getLLMResponse(chatDto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(llmResponse);
    }
}
