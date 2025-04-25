package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.response.embedding.EmbeddingResponseDto;

import java.util.List;

public interface EmbeddingService {
    // 책 임베딩 점수 평균 값 가져오기
    EmbeddingResponseDto getAverageEmbedding(List<Integer> bookIds);
}
