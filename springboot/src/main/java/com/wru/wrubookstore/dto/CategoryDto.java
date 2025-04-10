package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class CategoryDto {
    private String categoryType; // 분류 타입 - 대/중/소

    private String categoryLargeId;
    private String categoryLargeName;
    private String categoryMediumId;
    private String categoryMediumName;
    private String categorySmallId;
    private String categorySmallName;

    private Integer bookId;             // 책 코드
    private String publisherId;         // 출판사 코드
    private String name;                // 책 이름
    private String translator;          // 역자
    private Integer originalPrice;          // 정가
    private BigDecimal discountPercent; // 할인율 1 ~ 0.00
    private Integer discountPrice;          // 할인되는 가격
    private Integer salePrice;              // 판매가
    private Date releaseDate;           // 등록일
    private Date regDate;               // 판매일
    private String tableOfContent;      // 도서 목차
    private String description;         // 도서 소개
    private long isbn;                  // 책 고유번호
    private Integer stockQuantity;          // 재고 수량
    private String size;                // 도서 사이즈
    private Integer weight;                 // 도서 무게
    private Integer page;                   // 도서 페이지 수
    private String image;               // 도서의 이미지

    public CategoryDto() {}

    public CategoryDto(String categoryLargeId, String categoryLargeName, String categoryMediumId, String categoryMediumName, String categorySmallId, String categorySmallName) {
        this.categoryLargeId = categoryLargeId;
        this.categoryLargeName = categoryLargeName;
        this.categoryMediumId = categoryMediumId;
        this.categoryMediumName = categoryMediumName;
        this.categorySmallId = categorySmallId;
        this.categorySmallName = categorySmallName;
    }
}