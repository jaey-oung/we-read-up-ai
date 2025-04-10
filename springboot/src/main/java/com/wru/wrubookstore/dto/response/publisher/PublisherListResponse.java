package com.wru.wrubookstore.dto.response.publisher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PublisherListResponse {
    private String publisherId; // 출판사 코드
    private String name;        // 출판사 이름
}
