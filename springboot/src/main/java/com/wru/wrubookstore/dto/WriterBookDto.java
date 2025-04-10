package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriterBookDto {
    private String writerBookId;        // writer_book_코드
    private String writerId;            // 지은이 코드
    private Integer bookId;             // 책 코드

    public WriterBookDto(String writerBookId, String writerId, Integer bookId) {
        this.writerBookId = writerBookId;
        this.writerId = writerId;
        this.bookId = bookId;
    }
}
