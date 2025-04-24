package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CompleteBookDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
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

    // 판매량 기준 상위 5권 도서 리스트 반환
    @Override
    public List<RankedBookDto> getWeeklyRanking() throws Exception {
        List<RankedBookDto> rankedBooks = new ArrayList<>();
        // 판매량 기준 상위 5권 bookId 조회
        List<Integer> bookIds = orderRepository.selectBookIdInSalesRank();
        for (int i = 0; i < bookIds.size(); i++) {
            int bookId = bookIds.get(i);
            // 도서 정보, 카테고리 경로, 저자 정보 반환
            rankedBooks.add(bookRepository.selectRankedBookInfo(bookId));
        }
        return rankedBooks;
    }

    // 대분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> getAllLargeCategories() throws Exception {
        return bookRepository.selectAllLargeCategories();
    }

    // 중분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> getAllMediumCategories(String categoryLargeId) throws Exception {
        return bookRepository.selectAllMediumCategories(categoryLargeId);
    }

    // 소분류 카테고리 리스트 조회
    @Override
    public List<CategoryResponse> getAllSmallCategories(String categoryMediumId) throws Exception {
        return bookRepository.selectAllSmallCategories(categoryMediumId);
    }

    // 특정 카테고리에 속한 도서 개수 조회
    @Override
    public int getCntByCategoryIds(CategoryRequest category) throws Exception {
        return bookRepository.selectCntByCategoryIds(category);
    }

    // 특정 카테고리에 속한 도서 리스트 조회
    @Override
    public List<CompleteBookDto> getAllCompleteBooks(BookFilterDto request) throws Exception {
        return bookRepository.selectByCategory(request);
    }

    // 검색 결과 개수 조회
    @Override
    public int getCntBySearchCondition(HomeSearchCondition sc) throws Exception {
        return bookRepository.selectCntBySearchCondition(sc);
    }

    // 검색 창에서 특정 키워드를 포함하는 도서 리스트 조회
    @Override
    public List<CompleteBookDto> getAllCompleteBooks(HomeSearchCondition sc) throws Exception {
        /* 어떤 검색 옵션을 통한 검색인지 가져오기 */
        String option = sc.getOption();

        /* 검색 옵션에 따라 도서 리스트 반환 */
        return switch (option) {
            case "all" -> bookRepository.searchByAll(sc);
            case "title" ->  bookRepository.searchByTitle(sc);
            case "writer" -> bookRepository.searchByWriter(sc);
            default -> throw new Exception("잘못된 옵션입니다.");
        };
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
}
