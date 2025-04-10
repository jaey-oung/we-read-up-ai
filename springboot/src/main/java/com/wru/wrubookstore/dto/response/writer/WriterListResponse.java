package com.wru.wrubookstore.dto.response.writer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriterListResponse {
    private String name;        // 지은이 이름
    private Integer bookId;     // 책 코드
}
