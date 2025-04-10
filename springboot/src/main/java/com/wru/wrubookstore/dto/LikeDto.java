package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeDto {
    private Integer likeId;
    private Integer bookId;
    private Integer memberId;

    public LikeDto(){}

    public LikeDto(Integer bookId, Integer memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }
}
