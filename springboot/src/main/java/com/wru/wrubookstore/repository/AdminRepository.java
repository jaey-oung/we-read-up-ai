package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;

import java.util.List;
import java.util.Map;

public interface AdminRepository {

    // 검색
    List<BookDto> searchBook(String name) throws Exception;
    // 재고 0이아닌 상품 전부 조회
    List<BookDto> selectZeroNotQuantityBook(Map map) throws Exception;

    // 재고0인 상품 전부 조회
    List<BookDto> selectZeroQuantityBook(Map map) throws Exception;

    // writer-book 마지막 코드 조회용
    String selectWriterBookId() throws Exception;

    // 책 한권 조회용(isbn)
    BookDto selectBook(BookDto bookDto) throws Exception;

    // 출판사 마지막 코드 조회용
    String selectPublisherId() throws Exception;

    // 출판사 모두 조회용
    List<PublisherDto> selectAllPublisher() throws Exception;

    // 출판사 한개 조회용
    PublisherDto selectPublisherOne(PublisherDto publisherDto) throws Exception;

    // 지은이 모두 조회용
    List<WriterDto> selectAllWriter() throws Exception;

    String selectWriterId() throws Exception;

    WriterDto selectWriterOne(WriterDto writerDto) throws Exception;

    void insertBook(BookDto bookDto) throws Exception;

    void insertPublisher(PublisherDto publisherDto) throws Exception;

    void insertWriter(WriterDto writerDto) throws Exception;

    void insertWriterBook(WriterBookDto writerBookDto) throws Exception;


}
