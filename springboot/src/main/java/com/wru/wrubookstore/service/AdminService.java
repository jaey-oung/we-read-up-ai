package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminBookCreateData;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;

import java.util.List;
import java.util.Map;

public interface AdminService {

    // 책 등록
    String createBook(AdminResponse adminResponse) throws Exception;

    // 책 리스트 조회
    List<BookDto> getBookList(Integer quantity, Integer offset, Integer limit, String searchWord) throws Exception;

    // 재고 100개 추가
    String addQuantity(BookListResponse[] bookListResponse) throws Exception;

    // 책 한권 삭제
    String deleteMultipleBook(Integer[] bookId) throws Exception;

    // 카테고리 조회
    List<CategoryResponse> selectCategory(CategoryResponse categoryResponse) throws Exception;

    // 책 등록에 필요한 데이터 조회
    AdminBookCreateData selectBookCreateData() throws Exception;

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

    int insertBook(BookDto bookDto) throws Exception;

    int insertPublisher(PublisherDto publisherDto) throws Exception;

    int insertWriter(WriterDto writerDto) throws Exception;

    int insertWriterBook(WriterBookDto writerBookDto) throws Exception;

    String selectWriterId() throws Exception;

    // 지은이 비교 조회 서비스 로직
    List<String> processWriter(List<WriterDto> writerDto) throws Exception;
    // 출판사 비교 조회 서비스 로직
    String processPublisher(List<PublisherDto> publisherDto) throws Exception;
    // 책 비교 조회 서비스 로직
    int processBook(List<BookDto> bootDto, String publisherId) throws Exception;
    // writer_book 등록 서비스 로직
    void processWriterBook(List<String> writerIdList, Integer bookId) throws Exception;
    // 지은이 검색용
    List<WriterDto> searchWriter(String keyword) throws Exception;
    // 출판사 검색용
    List<PublisherDto> searchPublisher(String keyword) throws Exception;
}
