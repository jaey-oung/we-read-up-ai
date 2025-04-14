package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@ToString
public class BookDto {
    private Integer bookId;             // 책 코드 PK Auto_Increment
    private String publisherId;         // 출판사 코드
    private String categoryLargeId;     // 대_카테고리 코드
    private String categoryMediumId;    // 중_카테고리 코드 (nullable)
    private String categorySmallId;     // 소_카테고리 코드 (nullable)
    private String name;                // 책 이름
    private String translator;          // 역자 (nullable)
    private int originalPrice;          // 정가
    private BigDecimal discountPercent; // 할인율 1 ~ 0.00
    private int discountPrice;          // 할인되는 가격
    private int salePrice;              // 판매가
    private Date releaseDate;           // 등록일
    private Date regDate;               // 판매일
    private String tableOfContent;      // 도서 목차 (nullable)
    private String description;         // 도서 소개
    private long isbn;                  // 책 고유번호
    private int stockQuantity;          // 재고 수량
    private String size;                // 도서 사이즈
    private int weight;                 // 도서 무게
    private int page;                   // 도서 페이지 수
    private String image;               // 도서의 이미지

    public BookDto(){}

    public BookDto(String publisherId, String categoryLargeId, String categoryMediumId, String categorySmallId, String name, String translator, int originalPrice, BigDecimal discountPercent, int discountPrice, int salePrice, Date releaseDate, Date regDate, String tableOfContent, String description, long isbn, int stockQuantity, String size, int weight, int page, String image) {
        this.publisherId = publisherId;
        this.categoryLargeId = categoryLargeId;
        this.categoryMediumId = categoryMediumId;
        this.categorySmallId = categorySmallId;
        this.name = name;
        this.translator = translator;
        this.originalPrice = originalPrice;
        this.discountPercent = discountPercent;
        this.discountPrice = discountPrice;
        this.salePrice = salePrice;
        this.releaseDate = releaseDate;
        this.regDate = regDate;
        this.tableOfContent = tableOfContent;
        this.description = description;
        this.isbn = isbn;
        this.stockQuantity = stockQuantity;
        this.size = size;
        this.weight = weight;
        this.page = page;
        this.image = image;
    }

    public void setReleaseDate(Date releaseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(releaseDate);

        try{
            this.releaseDate = sdf.parse(formattedDate);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
