package com.wru.wrubookstore.dto.response.admin;

import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AdminBookCreateData {
    List<CategoryResponse> categoryResponse;    // 카테고리 데이터
    List<WriterDto> writerDto;                  // 모든 지은이 데이터
    List<PublisherDto> publisherDto;            // 모든 출판사 데이터
}
