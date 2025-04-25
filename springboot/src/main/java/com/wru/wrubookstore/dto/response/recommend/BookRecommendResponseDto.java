package com.wru.wrubookstore.dto.response.recommend;

import lombok.Getter;
import lombok.Setter;

// 도서 추천 응답 DTO
// 사용자의 성향 분석 결과에 따라 추천된 단일 도서 정보를 담음
@Getter
@Setter
public class BookRecommendResponseDto {
    private int book_id;
    private String title;
    private String author;
    private String image;
    private String description;
}
