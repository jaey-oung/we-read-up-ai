package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public interface AdminRepository {

    // 재고 100개 추가
    int addQuantity(BookListResponse bookListResponse) throws Exception;

    // 책 한권 삭제
    int deleteMultipleBook(Integer bookId) throws Exception;

    // 검색
    List<BookDto> searchBook(String searchWord) throws Exception;

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

    int insertBook(BookDto bookDto) throws Exception;

    int insertPublisher(PublisherDto publisherDto) throws Exception;

    int insertWriter(WriterDto writerDto) throws Exception;

    int insertWriterBook(WriterBookDto writerBookDto) throws Exception;

    // 지은이 검색용
    List<WriterDto> searchWriter(String keyword) throws Exception;
    // 출판사 검색용
    List<PublisherDto> searchPublisher(String keyword) throws Exception;

}
