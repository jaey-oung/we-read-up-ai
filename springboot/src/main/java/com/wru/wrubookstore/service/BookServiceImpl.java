package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CategoryDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.publisher.PublisherListResponse;
import com.wru.wrubookstore.dto.response.writer.WriterListResponse;
import com.wru.wrubookstore.repository.BookRepository;
import com.wru.wrubookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    public BookServiceImpl(BookRepository bookRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    // 카테고리 정보 조회
    @Override
    public CategoryDto selectCategoryInfo(String category) throws Exception {
        return bookRepository.selectCategoryInfo(category);
    }

    // 카테고리에 속한 책들의 수 조회
    @Override
    public int selectByCategoryCnt(String category) throws Exception {
        return bookRepository.selectByCategoryCnt(category);
    }

    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory(MainSearchCondition sc) throws Exception {
        return bookRepository.selectByCategory(sc);
    }

    // 낮은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory2(MainSearchCondition sc) throws Exception {
        return bookRepository.selectByCategory2(sc);
    }

    // 높은 가격 순
    // 카테고리에 속한 책들의 정보 조회
    @Override
    public List<CategoryDto> selectByCategory3(MainSearchCondition sc) throws Exception {
        return bookRepository.selectByCategory3(sc);
    }

    // 책 번호로 한 개 조회
    @Override
    public int countAllByAdmin() throws Exception {
        return bookRepository.countAllByAdmin();
    }

    @Override
    public List<BookDto> selectAllByAdmin() throws Exception {
        return bookRepository.selectAllByAdmin();
    }

    @Override
    public void deleteAllByAdmin() throws Exception {
        bookRepository.deleteAllByAdmin();
    }

    @Override
    public void updateByAdmin(BookListResponse bookListResponse) throws Exception {
        bookRepository.updateByAdmin(bookListResponse);
    }

    @Override
    public void deleteByAdmin(BookListResponse bookListResponse) throws Exception{
        bookRepository.deleteByAdmin(bookListResponse);
    }

    @Override
    public int countQuantityZeroByAdmin() throws Exception{
        return bookRepository.countQuantityZeroByAdmin();
    }

    @Override
    public List<BookDto> selectBook(Map map) throws Exception{
        return bookRepository.selectBook(map);
    }

    // 카테고리 조회용
    @Override
    public List<CategoryResponse> selectCategoryLarge() throws Exception{
        return bookRepository.selectCategoryLarge();
    }
    @Override
    public List<CategoryResponse> selectCategoryMedium(CategoryResponse categoryResponse) throws Exception{
        return bookRepository.selectCategoryMedium(categoryResponse);
    }
    @Override
    public List<CategoryResponse> selectCategorySmall(CategoryResponse categoryResponse) throws Exception{
        return bookRepository.selectCategorySmall(categoryResponse);
    }

    // 책 번호로 한개 조회
    @Override
    public BookDto select(Integer bookId) throws Exception{
        return bookRepository.select(bookId);
    }

    // 테스트용 insert
    @Override
    public int insert(BookDto book) throws Exception{
        return bookRepository.insert(book);
    }
    // 각 책의 지은이들을 조회
    @Override
    public List<String> selectWriter(Integer bookId) throws Exception{
        return bookRepository.selectWriter(bookId);
    }
    // 각 책의 출판사를 조회
    @Override
    public String selectPublisher(Integer bookId) throws Exception{
        return bookRepository.selectPublisher(bookId);
    }

    //  도서 제목과 저자 이름으로 통합 검색
    @Override
    public List<BookDto> searchByAll(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByAll(sc);
    }

    // 도서 제목으로 검색
    @Override
    public List<BookDto> searchByTitle(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByTitle(sc);
    }

    // 저자 이름으로 검색
    @Override
    public List<BookDto> searchByWriter(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByWriter(sc);
    }

    // 검색 결과 개수 조회
    @Override
    public int selectSearchCnt(MainSearchCondition sc) throws Exception {
        return bookRepository.selectSearchCnt(sc);
    }

    // 카테고리 소, 중 검색 이름
    @Override
    public CategoryResponse selectCategorySM(Integer bookId) throws Exception{
        return bookRepository.selectCategorySM(bookId);
    }

    // 카테고리 대 검색 이름
    @Override
    public CategoryResponse selectCategoryL(CategoryResponse categoryResponse) throws Exception{
        return bookRepository.selectCategoryL(categoryResponse);
    }

    // 지은이 이름 조회
    @Override
    public List<WriterListResponse> selectWriterName(Integer bookId) throws Exception{
        return bookRepository.selectWriterName(bookId);
    }

    // 출판사 이름 조회
    @Override
    public PublisherListResponse selectPublisherName(String publisherId) throws Exception{
        return bookRepository.selectPublisherName(publisherId);
    }


    // 낮은 가격 순

    //  도서 제목과 저자 이름으로 통합 검색
    public List<BookDto> searchByAll2(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByAll2(sc);
    }

    // 도서 제목으로 검색
    public List<BookDto> searchByTitle2(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByTitle2(sc);
    }

    // 저자 이름으로 검색
    public List<BookDto> searchByWriter2(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByWriter2(sc);
    }

    // 높은 가격 순

    //  도서 제목과 저자 이름으로 통합 검색
    public List<BookDto> searchByAll3(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByAll3(sc);
    }

    // 도서 제목으로 검색
    public List<BookDto> searchByTitle3(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByTitle3(sc);
    }

    // 저자 이름으로 검색
    public List<BookDto> searchByWriter3(MainSearchCondition sc) throws Exception {
        return bookRepository.searchByWriter3(sc);
    }

    // 판매량 기준 상위 5권 도서 리스트 반환
    @Override
    public List<RankedBookDto> getWeeklyRanking() throws Exception {
        List<RankedBookDto> rankedBooks = new ArrayList<>();
        // 판매량 기준 상위 5권 bookId 조회
        List<Integer> bookIds = orderRepository.selectBookIdInSalesRank();
        for (int i = 0; i < bookIds.size(); i++) {
            int bookId = bookIds.get(i);
            // 책 정보와 카테고리 정보 가져오기
            CategoryDto bookAndCategoryInfo = bookRepository.selectRankedBookInfo(bookId);
            // 책 하나당 저자 정보 가져오기
            List<String> authors = bookRepository.selectWriter(bookId);
            rankedBooks.add(new RankedBookDto(bookAndCategoryInfo, authors));
        }
        return rankedBooks;
    }

}
