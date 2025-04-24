package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CategoryDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.publisher.PublisherListResponse;
import com.wru.wrubookstore.dto.response.writer.WriterListResponse;

import java.util.List;
import java.util.Map;

public interface BookRepository {

    // 해당 책의 카테고리 정보 모두 조회
    CategoryResponse selectCategoryAll(Integer bookId) throws Exception;

    // 출판사 이름 조회
    PublisherListResponse selectPublisherName(String publisherId) throws Exception;
    // 지은이 이름 조회
    List<WriterListResponse> selectWriterName(Integer bookId) throws Exception;
    // 카테고리 정보 조회
    CategoryDto selectCategoryInfo(String category) throws Exception;

    // 카테고리에 속한 책들의 수 조회
    int selectByCategoryCnt(String category) throws Exception;

    // 카테고리에 속한 책들의 정보 조회
    List<CategoryDto> selectByCategory(MainSearchCondition sc) throws Exception;

    // 낮은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    List<CategoryDto> selectByCategory2(MainSearchCondition sc) throws Exception;

    // 높은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    List<CategoryDto> selectByCategory3(MainSearchCondition sc) throws Exception;

    // 관리자용
    int countAllByAdmin() throws Exception;
    List<BookDto> selectAllByAdmin() throws Exception;
    void deleteAllByAdmin() throws Exception;
    void updateByAdmin(BookListResponse bookListResponse) throws Exception;
    int countQuantityZeroByAdmin() throws Exception;
    List<BookDto> selectBook(Map map) throws Exception;

    // 카테고리 조회용
    List<CategoryResponse> selectCategoryLarge() throws Exception;
    List<CategoryResponse> selectCategoryMedium(CategoryResponse categoryResponse) throws Exception;
    List<CategoryResponse> selectCategorySmall(CategoryResponse categoryResponse) throws Exception;

    // 책 번호로 한개 조회
    BookDto select(Integer bookId) throws Exception;

    // 테스트용 insert
    int insert(BookDto book) throws Exception;

    // 각 책의 지은이들을 조회
    List<String> selectWriter(Integer bookId) throws Exception;

    // 각 책의 출판사를 조회
    String selectPublisher(Integer bookId) throws Exception;

    //  도서 제목과 저자 이름으로 통합 검색
    List<BookDto> searchByAll(MainSearchCondition sc) throws Exception;

    // 도서 제목으로 검색
    List<BookDto> searchByTitle(MainSearchCondition sc) throws Exception;

    // 저자 이름으로 검색
    List<BookDto> searchByWriter(MainSearchCondition sc) throws Exception;

    // 검색 결과 개수 조회
    int selectSearchCnt(MainSearchCondition sc) throws Exception;

    CategoryResponse selectCategorySM(Integer bookId) throws Exception;

    CategoryResponse selectCategoryL(CategoryResponse categoryResponse) throws Exception;

    // 낮은 가격 순
    //  도서 제목과 저자 이름으로 통합 검색
    List<BookDto> searchByAll2(MainSearchCondition sc) throws Exception;

    // 도서 제목으로 검색
    List<BookDto> searchByTitle2(MainSearchCondition sc) throws Exception;

    // 저자 이름으로 검색
    List<BookDto> searchByWriter2(MainSearchCondition sc) throws Exception;

    // 높은 가격 순
    //  도서 제목과 저자 이름으로 통합 검색
    List<BookDto> searchByAll3(MainSearchCondition sc) throws Exception;

    // 도서 제목으로 검색
    List<BookDto> searchByTitle3(MainSearchCondition sc) throws Exception;

    // 저자 이름으로 검색
    List<BookDto> searchByWriter3(MainSearchCondition sc) throws Exception;

    // 순위에 오른 도서의 카테고리와 도서 정보 조회
    CategoryDto selectRankedBookInfo(Integer bookId) throws Exception;
}
