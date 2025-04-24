package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CompleteBookDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SqlSessionTemplate session;

    public BookRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    private final String namespace = "com.wru.wrubookstore.mapper.BookMapper.";

    // 순위에 오른 도서의 카테고리와 도서 정보 조회
    @Override
    public RankedBookDto selectRankedBookInfo(Integer bookId) throws Exception {
        return session.selectOne(namespace + "selectRankedBookInfo", bookId);
    }

    // 대분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> selectAllLargeCategories() throws Exception {
        return session.selectList(namespace + "selectAllLargeCategories");
    }

    // 중분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> selectAllMediumCategories(String categoryLargeId) throws Exception {
        return session.selectList(namespace + "selectAllMediumCategories", categoryLargeId);
    }

    // 소분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> selectAllSmallCategories(String categoryMediumId) throws Exception {
        return session.selectList(namespace + "selectAllSmallCategories", categoryMediumId);
    }

    // 특정 카테고리에 속한 도서 개수 조회
    @Override
    public int selectCntByCategoryIds(CategoryRequest category) throws Exception {
        return session.selectOne(namespace + "selectCntByCategory", category);
    }

    // 특정 카테고리에 속한 도서 리스트 조회
    @Override
    public List<CompleteBookDto> selectByCategory(BookFilterDto request) throws Exception {
        return session.selectList(namespace + "selectByCategory", request);
    }

    // 검색 결과 개수 조회
    @Override
    public int selectCntBySearchCondition(HomeSearchCondition sc) throws Exception {
        return session.selectOne(namespace + "selectSearchCnt", sc);
    }

    //  도서 제목과 저자 이름으로 통합 검색 시 도서 정보 조회
    public List<CompleteBookDto> searchByAll(HomeSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByAll", sc);
    }

    // 도서 제목으로 검색 시 도서 정보 조회
    public List<CompleteBookDto> searchByTitle(HomeSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByTitle", sc);
    }

    // 저자 이름으로 검색 시 도서 정보 조회
    public List<CompleteBookDto> searchByWriter(HomeSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByWriter", sc);
    }

    // 해당 책의 카테고리 정보 모두 조회
    @Override
    public CategoryResponse selectCategoryAll(Integer bookId) throws Exception{
        return session.selectOne(namespace + "selectCategoryAll", bookId);
    }

    // 관리자용
    @Override
    public int countAllByAdmin() throws Exception {
        return session.selectOne(namespace + "countAllByAdmin");
    }

    @Override
    public List<BookDto> selectAllByAdmin() throws Exception {
        return session.selectList(namespace + "selectAllByAdmin");
    }

    @Override
    public void deleteAllByAdmin() throws Exception {
        session.delete(namespace + "deleteAllByAdmin");
    }

    @Override
    public void updateByAdmin(BookListResponse bookListResponse) throws Exception {
        session.update(namespace + "updateByAdmin", bookListResponse);
    }

    @Override
    public int countQuantityZeroByAdmin() throws Exception{
        return session.selectOne(namespace + "countQuantityZeroByAdmin");
    }

    @Override
    public List<BookDto> selectBook(Map map) throws Exception{
        return session.selectList(namespace + "selectBook", map);
    }

    // 카테고리 조회용
    @Override
    public List<CategoryResponse> selectCategoryLarge() throws Exception{
        return session.selectList(namespace + "selectCategoryLarge");
    }
    @Override
    public List<CategoryResponse> selectCategoryMedium(CategoryResponse categoryResponse) throws Exception{
        return session.selectList(namespace + "selectCategoryMedium", categoryResponse);
    }
    @Override
    public List<CategoryResponse> selectCategorySmall(CategoryResponse categoryResponse) throws Exception{
        return session.selectList(namespace + "selectCategorySmall", categoryResponse);
    }

    // 책 번호로 한개 조회
    @Override
    public BookDto select(Integer bookId) throws Exception{
        return session.selectOne(namespace + "select", bookId);
    }

    // 테스트용 insert
    @Override
    public int insert(BookDto book)  throws Exception{
        return session.insert(namespace + "insert", book);
    }

    // 각 책의 지은이들을 조회
    @Override
    public List<String> selectWriter(Integer bookId) throws Exception {
        return session.selectList(namespace + "selectWriter", bookId);
    }

    // 각 책의 출판사를 조회
    @Override
    public String selectPublisher(Integer bookId) throws Exception{
        return session.selectOne(namespace + "selectPublisher", bookId);
    }
}
