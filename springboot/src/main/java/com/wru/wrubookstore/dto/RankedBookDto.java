package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RankedBookDto {
    // 책 관련 정보
    private String bookId;
    private String name;
    private String image;

    // 카테고리 정보
    private String categoryLargeName;
    private String categoryMediumName;

    // 저자 정보
    private String writerNames;

    public RankedBookDto() {}

}
