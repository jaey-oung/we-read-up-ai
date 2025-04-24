package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.ReviewDto;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import com.wru.wrubookstore.error.exception.*;
import com.wru.wrubookstore.validator.MemberValidator;
import com.wru.wrubookstore.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberValidator memberValidator;

    private final int FAIL = 0;
    private final int ZERO_RATING = 0;

    ReviewServiceImpl(ReviewRepository reviewRepository, MemberValidator memberValidator) {
        this.reviewRepository = reviewRepository;
        this.memberValidator = memberValidator;
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
    public String insertReview(ReviewDto reviewDto, Integer userId) throws Exception{

        // 아이디 검증
        MemberDto memberDto = memberValidator.getValidMember(userId);

        // 내용이 없거나, 별점을 안눌렀으면 리턴
        if (reviewDto.getContent().isEmpty() && reviewDto.getRating() == ZERO_RATING) {
            return "all is empty";
        } else if (reviewDto.getRating() == ZERO_RATING) {
            return "rating is empty";
        } else if (reviewDto.getContent().isEmpty()) {
            return "content is empty";
        }

        int result = reviewRepository.insertReview(reviewDto);

        if(result == FAIL){
            throw new ReviewInsertFailException("리뷰 작성에 실패했습니다.", reviewDto);
        }

        return "success";
    }

    // 해당 책에 리뷰 삭제
    @Override
    public String deleteReview(ReviewDto reviewDto,Integer userId) throws Exception{

        // 아이디 검증
        MemberDto memberDto = memberValidator.getValidMember(userId);

        // 세션의 로그인중인 유저의 memberId와 등록된 리뷰의 memberId가 같지 않으면 에러
        if(!memberDto.getMemberId().equals(reviewDto.getMemberId())){
            throw new ReviewAuthorMismatchException("리뷰 작성자만 해당 리뷰를 삭제할 수 있습니다.",reviewDto,memberDto.getMemberId());
        }

        int result = reviewRepository.deleteReview(reviewDto);
        // 리뷰 삭제에 실패했을 경우
        if(result == FAIL){
            throw new ReviewDeleteFailException("리뷰 삭제에 실패하였습니다.", reviewDto);
        }

        return "success";
    }

    // 해당 책에 리뷰 수정
    @Override
    public String modifyReview(ReviewDto reviewDto, Integer userId) throws Exception{

        // 아이디 검증
        MemberDto memberDto = memberValidator.getValidMember(userId);

        // 세션의 로그인중인 유저의 memberId와 등록된 리뷰의 memberId가 같지 않으면 에러
        if(!memberDto.getMemberId().equals(reviewDto.getMemberId())){
            throw new ReviewAuthorMismatchException("리뷰 작성자만 해당 리뷰를 수정할 수 있습니다.",reviewDto,memberDto.getMemberId());
        }

        // 리뷰 수정에 별점을 주지 않았을 경우
        if(reviewDto.getRating() == null || reviewDto.getRating() == ZERO_RATING){
            return "ratingError";
        }
        // 리뷰 수정에 내용을 입력하지 않았을 경우
        else if(reviewDto.getContent() == null || reviewDto.getContent().isEmpty()) {
            return "contentError";
        }

        int result = reviewRepository.modifyReview(reviewDto);

        // 리뷰 수정에 실패했을 경우 발생
        if(result == FAIL){
            throw new ReviewModifyFailException("리뷰 수정에 실패하였습니다.", reviewDto);
        }

        return "success";
    }
    // 해당 책의 등록된 리뷰의 점수 조회
    @Override
    public double ratingReview(Integer bookId) throws Exception{
        return reviewRepository.ratingReview(bookId);
    }
}
