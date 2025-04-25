package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.response.recommend.BookRecommendResponseDto;

import java.util.List;
import java.util.Map;

public interface BookRecommendService {
    List<BookRecommendResponseDto> recommendBooks(Integer userId, Map<String, Float> surveyScores);
}
