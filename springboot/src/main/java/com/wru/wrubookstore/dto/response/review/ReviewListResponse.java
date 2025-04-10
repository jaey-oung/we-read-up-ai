package com.wru.wrubookstore.dto.response.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReviewListResponse {
    private Integer reviewId;
    private Integer memberId;   // 멤버 아이디
    private Integer rating;     // 리뷰 별점
    private String nickname;    // 회원 닉네임
    private String content;     // 리뷰 내용
    private Date regDate;       // 리뷰 작성일
}
