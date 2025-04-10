package com.wru.wrubookstore.dto.response.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookListResponse {
    private Integer bookId;
    private Integer stockQuantity;

    BookListResponse() {}
}
