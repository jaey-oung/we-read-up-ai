package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReviewDto {
    private Integer reviewId;   // PK
    private Integer bookId;     // 책 ID
    private Integer memberId;   // 회원 ID
    private String content;     // 내용
    private Integer rating;     // 별점
    private Date regDate;       // 등록일
    private Date modDate;       // 수정일

    ReviewDto(){}

    public ReviewDto(Integer bookId, Integer memberId, String content, Integer rating) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.content = content;
        this.rating = rating;
    }
}
