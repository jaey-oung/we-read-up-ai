package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CategoryDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.publisher.PublisherListResponse;
import com.wru.wrubookstore.dto.response.writer.WriterListResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SqlSessionTemplate session;

    public BookRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    private final String namespace = "com.wru.wrubookstore.mapper.BookMapper.";

    // 카테고리 정보 조회
    @Override
    public CategoryDto selectCategoryInfo(String category) throws Exception {
        List<Object> list = session.selectList(namespace + "selectCategoryInfo", category);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("유효한 카테고리가 아닙니다.");
        }
        CategoryDto categoryDto = (CategoryDto) list.get(0);
        categoryDto.setCategoryType(category.substring(0, 2));
        return categoryDto;
    }

    // 카테고리에 속한 책들의 수 조회
    @Override
    public int selectByCategoryCnt(String category) throws Exception {
        return session.selectOne(namespace + "selectByCategoryCnt", category);
    }

    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory(MainSearchCondition sc) throws Exception {
        List<CategoryDto> list = session.selectList(namespace + "selectByCategory", sc);
        list.get(0).setCategoryType(sc.getCategory().substring(0, 2));
        return list;
    }

    // 낮은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory2(MainSearchCondition sc) throws Exception {
        List<CategoryDto> list = session.selectList(namespace + "selectByCategory2", sc);
        list.get(0).setCategoryType(sc.getCategory().substring(0, 2));
        return list;
    }

    // 높은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory3(MainSearchCondition sc) throws Exception {
        List<CategoryDto> list = session.selectList(namespace + "selectByCategory3", sc);
        list.get(0).setCategoryType(sc.getCategory().substring(0, 2));
        return list;
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

    @Override
    public void deleteByAdmin(BookListResponse bookListResponse) throws Exception{
        session.delete(namespace + "deleteByAdmin", bookListResponse);
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
    // 카테고리 조회용

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

    //  도서 제목과 저자 이름으로 통합 검색
    public List<BookDto> searchByAll(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByAll", sc);
    }

    // 도서 제목으로 검색
    public List<BookDto> searchByTitle(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByTitle", sc);
    }

    // 저자 이름으로 검색
    public List<BookDto> searchByWriter(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByWriter", sc);
    }

    // 검색 결과 개수 조회
    @Override
    public int selectSearchCnt(MainSearchCondition sc) throws Exception {
        return session.selectOne(namespace + "selectSearchCnt", sc);
    }

    // 카테고리 소, 중 검색 이름
    @Override
    public CategoryResponse selectCategorySM(Integer bookId) throws Exception{
        return session.selectOne(namespace + "selectCategorySM", bookId);
    }

    // 카테고리 대 검색 이름
    @Override
    public CategoryResponse selectCategoryL(CategoryResponse categoryResponse) throws Exception{
        return session.selectOne(namespace + "selectCategoryL", categoryResponse);
    }

    // 지은이 이름 조회
    @Override
    public List<WriterListResponse> selectWriterName(Integer bookId) throws Exception{
        return session.selectList(namespace + "selectWriterName", bookId);
    }

    // 출판사 이름 조회
    @Override
    public PublisherListResponse selectPublisherName(String publisherId) throws Exception{
        return session.selectOne(namespace + "selectPublisherName", publisherId);
    }

    // 낮은 가격 순

    //  도서 제목과 저자 이름으로 통합 검색
    public List<BookDto> searchByAll2(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByAll2", sc);
    }

    // 도서 제목으로 검색
    public List<BookDto> searchByTitle2(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByTitle2", sc);
    }

    // 저자 이름으로 검색
    public List<BookDto> searchByWriter2(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByWriter2", sc);
    }

    // 높은 가격 순

    //  도서 제목과 저자 이름으로 통합 검색
    public List<BookDto> searchByAll3(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByAll3", sc);
    }

    // 도서 제목으로 검색
    public List<BookDto> searchByTitle3(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByTitle3", sc);
    }

    // 저자 이름으로 검색
    public List<BookDto> searchByWriter3(MainSearchCondition sc) throws Exception {
        return session.selectList(namespace + "searchByWriter3", sc);
    }

    // 순위에 오른 도서의 카테고리와 도서 정보 조회
    @Override
    public CategoryDto selectRankedBookInfo(Integer bookId) throws Exception {
        return session.selectOne(namespace + "selectRankedBookInfo", bookId);
    }
}
