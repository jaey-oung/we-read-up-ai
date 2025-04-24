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


    private List<PublisherDto> publisherDto;  // 출판사 모음


    private List<WriterDto> writerDto;        // 지은이 모음

    AdminResponse(){}
}
