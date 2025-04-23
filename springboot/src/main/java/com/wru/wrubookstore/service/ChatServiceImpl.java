package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.ChatDto;
import com.wru.wrubookstore.repository.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final WebClient webClient;

    public ChatServiceImpl(ChatRepository chatRepository, WebClient webClient) {
        this.chatRepository = chatRepository;
        this.webClient = webClient;
    }

    // 로그인 사용자 채팅 내역 출력
    @Override
    public List<ChatDto> selectAllByUserId(Integer userId) throws Exception {
        return chatRepository.selectAllByUserId(userId);
    }

    // 비로그인 사용자 채팅 내역 출력
    @Override
    public List<ChatDto> selectAllByUuid(String uuid) throws Exception {
        return chatRepository.selectAllByUuid(uuid);
    }

    // 채팅 저장
    @Override
    public int insert(ChatDto chatDto) throws Exception {
        return chatRepository.insert(chatDto);
    }

    // Fast API와 통신하여 LLM 답변 가져오기
    @Override
    public Mono<ChatDto> getLLMResponse(ChatDto chatDto) throws Exception {
        return webClient.post()
                .uri("/chat")
                .bodyValue(chatDto) // bodyValue 값 JSON으로 변경 후 자동 직렬화
                .retrieve() // 요청 후 응답 수신
                .bodyToMono(ChatDto.class) // 응답 JSON 해당 타입으로 역직렬화
                .flatMap(response -> { // Mono로 감싸지 않은 응답 값 response에 저장
                    try {
                        chatRepository.insert(response);
                        return Mono.just(response);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
