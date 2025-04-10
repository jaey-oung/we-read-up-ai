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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReviewController {
    private final MemberService memberService;
    private final ReviewService reviewService;

    ReviewController(ReviewService reviewService, MemberService memberService) {
        this.reviewService = reviewService;
        this.memberService = memberService;
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
    public String reviewUpdate(@RequestBody ReviewDto reviewDto, HttpSession session) {
        try{
            // 리뷰 삭제를 하려면 memberId와 세션멤버의 아이디가같아야함
            // 세션에서 이메일을 받아와서 이메일을 넣으면 user_id를 뱉어주고
            // user_id로 member_id를 받아와서 세션에서 받아온 memberId와 넘어온 memberId가 같으면 삭제

            System.out.println("ReviewUpdate//reviewDto = " + reviewDto);

            // 세션에 등록된 email 받아오기
            int userId = 0;

            // 멤버 정보 조회
            if((Boolean)session.getAttribute("isMember")){
                userId = (Integer)session.getAttribute("userId");
            }

            // memberId 받아오기
            MemberDto memberDto = memberService.selectMember(userId);

            System.out.println("ReviewUpdate//memberDto = " + memberDto);

            // 비회원이면
            if(memberDto == null){
                throw new Exception("No member found");
            }

            // 회원이고 리뷰 작성 당사자이면
            if(reviewDto.getRating() == null || reviewDto.getRating() == 0) {
                return "ratingError";
            }
            else if(reviewDto.getContent() == null || reviewDto.getContent().isEmpty()) {
                return "contentError";
            }
            else if(memberDto.getMemberId().equals(reviewDto.getMemberId())){
                reviewService.modifyReview(reviewDto);

                return "success";
            }
            else{
                return "fail";
            }

        } catch(Exception e){
            e.printStackTrace();
            return "NoMember";
        }
    }

    @PostMapping("/reviewDelete")
    @ResponseBody
    public String reviewDelete(@RequestBody ReviewDto reviewDto, HttpSession session) {
        try{
            // 리뷰 삭제를 하려면 memberId와 세션멤버의 아이디가같아야함
            // 세션에서 이메일을 받아와서 이메일을 넣으면 user_id를 뱉어주고
            // user_id로 member_id를 받아와서 세션에서 받아온 memberId와 넘어온 memberId가 같으면 삭제

            System.out.println("ReviewDelect//reviewDto = " + reviewDto);

            // 세션에 등록된 email 받아오기
            int userId = 0;

            // 멤버 정보 조회
            if((Boolean)session.getAttribute("isMember")){
                userId = (Integer)session.getAttribute("userId");
                System.out.println("userId = " + userId);
            }

            // memberId 받아오기
            MemberDto memberDto = memberService.selectMember(userId);

            System.out.println("ReviewDelect//memberDto = " + memberDto);

            // 비회원이면
            if(memberDto == null){
                throw new Exception("No member found");
            }

            // 회원이고 리뷰 작성 당사자이면
            if(memberDto.getMemberId().equals(reviewDto.getMemberId())){
                reviewService.deleteReview(reviewDto);

                return "success";
            } else{
                return "fail";
            }

        } catch(Exception e){
            e.printStackTrace();
            return "NoMember";
        }
    }

    @PostMapping("/reviewInsert")
    @ResponseBody
    public String reviewInsert(@RequestBody ReviewDto reviewDto){

        System.out.println("reviewDto = " + reviewDto);
        try{
            // 내용이 없거나, 별점을 안눌렀으면 에러
            if (reviewDto.getContent().isEmpty() && reviewDto.getRating() == 0) {
                throw new Exception("all is empty");
            } else if(reviewDto.getRating() == 0){
                throw new Exception("rating is empty");
            } else if(reviewDto.getContent().isEmpty()) {
                throw new Exception("content is empty");
            }

            // DB에 review 추가
            reviewService.insertReview(reviewDto);

            return "success";
        } catch(Exception e){
            e.printStackTrace();
            // 내용이 없거나 별점이 안눌렸을때 보여줄 alert 선택용
            if(e.getMessage().equals("all is empty")){
                return "all";
            } else if(e.getMessage().equals("rating is empty")){
                return "rating";
            } else if(e.getMessage().equals("content is empty")){
                return "content";
            }
            return "error";
        }
    }
}
