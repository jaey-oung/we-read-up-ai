package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CompleteBookDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;

import java.util.List;
import java.util.Map;

public interface BookService {

    // 판매량 기준 상위 5권 도서 리스트 반환
    List<RankedBookDto> getWeeklyRanking() throws Exception;

    // 대분류 카테고리 리스트 조회
    List<CategoryResponse> getAllLargeCategories() throws Exception;

    // 중분류 카테고리 리스트 조회
    List<CategoryResponse> getAllMediumCategories(String categoryLargeId) throws Exception;

    // 소분류 카테고리 리스트 조회
    List<CategoryResponse> getAllSmallCategories(String categoryMediumId) throws Exception;

    // 특정 카테고리에 속한 도서 개수 조회
    int getCntByCategoryIds(CategoryRequest category) throws Exception;

    // 특정 카테고리에 속한 도서 리스트 조회
    List<CompleteBookDto> getAllCompleteBooks(BookFilterDto request) throws Exception;

    // 검색 결과 개수 조회
    int getCntBySearchCondition(HomeSearchCondition sc) throws Exception;

    // 검색 창에서 특정 키워드를 포함하는 도서 리스트 조회
    List<CompleteBookDto> getAllCompleteBooks(HomeSearchCondition request) throws Exception;

    // 관리자용
    int countAllByAdmin() throws Exception;
    List<BookDto> selectAllByAdmin() throws Exception;
    void deleteAllByAdmin() throws Exception;
    void updateByAdmin(BookListResponse bookListResponse) throws Exception;
    int countQuantityZeroByAdmin() throws Exception;
    void deleteByAdmin(BookListResponse bookListResponse) throws Exception;
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

    // 카테고리 소, 중 검색 이름
    CategoryResponse selectCategorySM(Integer bookId) throws Exception;

    // 카테고리 대 검색 이름
    CategoryResponse selectCategoryL(CategoryResponse categoryResponse) throws Exception;
}
