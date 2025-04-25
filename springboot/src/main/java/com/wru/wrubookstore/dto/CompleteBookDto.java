package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompleteBookDto {
    BookDto book;
    String publisherName;
    String writerNames;

    public CompleteBookDto() {}
}
