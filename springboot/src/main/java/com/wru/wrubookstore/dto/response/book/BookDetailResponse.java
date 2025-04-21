package com.wru.wrubookstore.dto.response.book;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookDetailResponse {
    // 현재 로그인한 맴버의 아이디
    MemberDto memberDto;
    // 좋아요 상태 조회
    int likeStatus;
    // 책 정보 조회
    BookDto bookDto;
    // 지은이들 조회
    List<String> writer;
    // 출판사 조회
    String publisher;
    // 리뷰들 조회
    List<ReviewListResponse> review;
    // 리뷰 점수 조회
    double rating;
    // 카테고리 조회
    CategoryResponse categoryResponse;
}
