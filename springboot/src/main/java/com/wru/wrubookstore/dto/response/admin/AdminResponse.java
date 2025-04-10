package com.wru.wrubookstore.dto.response.admin;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class AdminResponse {

    private List<BookDto> bookDto;      // 책 모음

//    private Integer bookId;             // 책 코드
//    private String writerBookId;        // writer_book_id
//    private String categorySmallId;     // 소_카테고리 코드
//    private String name;                // 책 이름
//    private String translator;          // 역자
//    private int originalPrice;          // 정가
//    private BigDecimal discountPercent; // 할인율 1 ~ 0.00
//    private int discountPrice;          // 할인되는 가격
//    private int salePrice;              // 판매가
//    private Date releaseDate;           // 등록일
//    private Date regDate;               // 판매일
//    private String image;               // 도서의 이미지
//    private String tableOfContent;      // 도서 목차
//    private String description;         // 도서 소개
//    private long isbn;                  // 책 고유번호
//    private int stockQuantity;          // 재고 수량
//    private String size;                // 도서 사이즈
//    private int weight;                 // 도서 무게
//    private int page;                   // 도서 페이지 수


    private List<PublisherDto> publisherDto;  // 출판사 모음


    private List<WriterDto> writerDto;        // 지은이 모음

    AdminResponse(){}
}
