package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriterDto {
    private String writerId;        // 지은이 코드
    private String name;            // 지은이 이름
    private String description;     // 지은이 설명
    private char gender;            // 지은이 성별
    private String nationality;     // 지은이 국적
    private String nickname;        // 지은이 필명

    WriterDto(){}
}
