package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.ReviewDto;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import com.wru.wrubookstore.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;

    ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 해당 책의 등록된 리뷰의 수 조회
    @Override
    public int countReview(Integer bookId) throws Exception{
        return reviewRepository.countReview(bookId);
    }

    // 해당 책의 등록된 리뷰를 전부 조회 (오래된 순)
    @Override
    public List<ReviewListResponse> selectReview(Integer bookId) throws Exception{
        return reviewRepository.selectReview(bookId);
    }

    // 해당 책에 리뷰 추가
    @Override
    public void insertReview(ReviewDto reviewDto) throws Exception{
        reviewRepository.insertReview(reviewDto);
    }

    // 해당 책에 리뷰 삭제
    @Override
    public void deleteReview(ReviewDto reviewDto) throws Exception{
        reviewRepository.deleteReview(reviewDto);
    }

    // 해당 책에 리뷰 수정
    @Override
    public void modifyReview(ReviewDto reviewDto) throws Exception{
        reviewRepository.modifyReview(reviewDto);
    }
    // 해당 책의 등록된 리뷰의 점수 조회
    @Override
    public double ratingReview(Integer bookId) throws Exception{
        return reviewRepository.ratingReview(bookId);
    }
}
