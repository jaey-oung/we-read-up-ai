package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.ReviewDto;
import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import com.wru.wrubookstore.service.MemberService;
import com.wru.wrubookstore.service.ReviewService;
import com.wru.wrubookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {
    private final ReviewService reviewService;

    ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviewList")
    public String reviewList() {

        return "board/review-list";
    }

    @GetMapping("/reviewDetail")
    public String reviewDetail() {

        return "board/review-detail";
    }

    @PostMapping("/reviewUpdate")
    @ResponseBody
    public String reviewUpdate(@RequestBody ReviewDto reviewDto,
                               @SessionAttribute(value="userId", required = false) Integer userId) throws Exception{
        // 리뷰 삭제를 하려면 memberId와 세션멤버의 아이디가같아야함
        // 세션에서 이메일을 받아와서 이메일을 넣으면 user_id를 뱉어주고
        // user_id로 member_id를 받아와서 세션에서 받아온 memberId와 넘어온 memberId가 같으면 삭제
        return reviewService.modifyReview(reviewDto, userId);
    }

    @PostMapping("/reviewDelete")
    @ResponseBody
    public String reviewDelete(@RequestBody ReviewDto reviewDto,
                               @SessionAttribute(value="userId", required = false) Integer userId) throws Exception {
        // 리뷰 삭제를 하려면 memberId와 세션멤버의 아이디가같아야함
        // 세션에서 이메일을 받아와서 이메일을 넣으면 user_id를 뱉어주고
        // user_id로 member_id를 받아와서 세션에서 받아온 memberId와 넘어온 memberId가 같으면 삭제
        return reviewService.deleteReview(reviewDto, userId);
    }

    @PostMapping("/reviewInsert")
    @ResponseBody
    public String reviewInsert(@RequestBody ReviewDto reviewDto,
                               @SessionAttribute(value="userId", required = false) Integer userId) throws Exception {
        // DB에 review 추가
        return reviewService.insertReview(reviewDto, userId);
    }
}
