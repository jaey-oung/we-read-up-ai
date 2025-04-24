package com.wru.wrubookstore.repository;

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

public interface BookRepository {

    // 순위에 오른 도서의 카테고리와 도서 정보 조회
    RankedBookDto selectRankedBookInfo(Integer bookId) throws Exception;

    // 대분류 카테고리 리스트 조회
    List<CategoryResponse> selectAllLargeCategories() throws Exception;

    // 중분류 카테고리 리스트 조회
    List<CategoryResponse> selectAllMediumCategories(String categoryLargeId) throws Exception;

    // 소분류 카테고리 리스트 조회
    List<CategoryResponse> selectAllSmallCategories(String categoryMediumId) throws Exception;

    // 특정 카테고리에 속한 도서 개수 조회
    int selectCntByCategoryIds(CategoryRequest category) throws Exception;

    // 특정 카테고리에 속한 도서 리스트 조회
    List<CompleteBookDto> selectByCategory(BookFilterDto request) throws Exception;

    // 검색 결과 개수 조회
    int selectCntBySearchCondition(HomeSearchCondition sc) throws Exception;

    // 도서 제목과 저자 이름으로 통합 검색 시 도서 정보 조회
    List<CompleteBookDto> searchByAll(HomeSearchCondition sc) throws Exception;

    // 도서 제목으로 검색 시 도서 정보 조회
    List<CompleteBookDto> searchByTitle(HomeSearchCondition sc) throws Exception;

    // 저자 이름으로 검색 시 도서 정보 조회
    List<CompleteBookDto> searchByWriter(HomeSearchCondition sc) throws Exception;

    // 해당 책의 카테고리 정보 모두 조회
    CategoryResponse selectCategoryAll(Integer bookId) throws Exception;

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
}
