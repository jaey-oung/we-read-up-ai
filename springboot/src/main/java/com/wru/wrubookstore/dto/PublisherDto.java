package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PublisherDto {
    private String publisherId;     // 출판사 코드
    private String name;            // 출판사 이름
    private String president;       // 출판사 대표
    private String bizRegNo;        // 사업자번호
    private String mainAddress;     // 기본 주소
    private String detailAddress;   // 상세 주소
    private Integer zipCode;        // 우편번호
    private String phoneNum;        // 핸드폰 번호
    private String bizArea;         // 국적

    PublisherDto(){}
}
