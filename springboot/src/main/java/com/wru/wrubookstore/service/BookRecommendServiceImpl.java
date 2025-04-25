package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.response.recommend.BookRecommendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookRecommendServiceImpl implements BookRecommendService {

    private final WebClient webClient;

    @Override
    public List<BookRecommendResponseDto> recommendBooks(Integer userId, Map<String, Float> surveyScores) {
        // FastAPI로 추천 도서 리스트 받아오기
        return webClient.post()
                .uri("/book/recommend")
                .bodyValue(Map.of("scores", surveyScores))  // JSON 구조 맞춰서 전송
                .retrieve()
                .bodyToFlux(BookRecommendResponseDto.class)
                .collectList()
                .block(); // 동기식으로 응답 받기
    }
}
