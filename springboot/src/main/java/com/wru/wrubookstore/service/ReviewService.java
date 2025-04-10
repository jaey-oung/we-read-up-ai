package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.ReviewDto;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    // 해당 책의 등록된 리뷰의 수 조회
    int countReview(Integer bookId) throws Exception;

    // 해당 책의 등록된 리뷰를 전부 조회 (오래된 순)
    List<ReviewListResponse> selectReview(Integer bookId) throws Exception;

    // 해당 책에 리뷰 추가
    void insertReview(ReviewDto reviewDto) throws Exception;

    // 해당 책에 리뷰 삭제
    void deleteReview(ReviewDto reviewDto) throws Exception;

    // 해당 책에 리뷰 수정
    void modifyReview(ReviewDto reviewDto) throws Exception;

    // 해당 책의 등록된 리뷰의 점수 조회
    double ratingReview(Integer bookId) throws Exception;
}
