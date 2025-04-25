package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.response.embedding.EmbeddingResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final WebClient webClient;

    public EmbeddingServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    // 도서 리스트의 평균 성향 점수 계산
    @Override
    public EmbeddingResponseDto getAverageEmbedding(List<Integer> bookIds) {
        return webClient.post()
                .uri("/embedding/average")
                .bodyValue(Map.of("book_ids", bookIds)) // body에 bookId 리스트 전달
                .retrieve() // FastAPI로부터 응답 수신
                .bodyToMono(EmbeddingResponseDto.class) // 응답 JSON을 DTO로 역직렬화
                .block(); // 동기 방식으로 결과 반환
    }
}
