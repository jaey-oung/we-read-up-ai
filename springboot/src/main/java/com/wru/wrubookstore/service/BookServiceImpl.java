package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CompleteBookDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.book.BookDetailResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.error.exception.BookNotFoundException;
import com.wru.wrubookstore.error.exception.PublisherNotFoundException;
import com.wru.wrubookstore.error.exception.WriterNotFoundException;
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
    private final LikeService likeService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    public BookServiceImpl(BookRepository bookRepository, OrderRepository orderRepository, LikeService likeService, ReviewService reviewService, MemberService memberService) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.likeService = likeService;
        this.reviewService = reviewService;
        this.memberService = memberService;
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

    // 해당 책의 카테고리 정보 모두 조회
    @Override
    public CategoryResponse selectCategoryAll(Integer bookId) throws Exception{
        return bookRepository.selectCategoryAll(bookId);
    }

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

    // 책 번호로 상품 정보 조회
    @Override
    public BookDetailResponse select(Integer bookId, Integer userId) throws Exception{

        // 리뷰가 없을때 ArithmeticException 방지용
        final int ZERO_RATING = 0;
        // 해당 책의 상품 정보, 지은이 정보, 출판사 정보, 리뷰 정보, 좋아요 정보, 카테고리 정보를 담을 Dto
        BookDetailResponse bookDetailResponse = new BookDetailResponse();

        // 현재 세션의 멤버 조회
        bookDetailResponse.setMemberDto(memberService.selectMember(userId));
        // 좋아요 상태 조회
        bookDetailResponse.setLikeStatus(likeService.selectLikeMember(bookId, userId));
        // 상품 정보 조회
        bookDetailResponse.setBookDto(bookRepository.select(bookId));
        if(bookDetailResponse.getBookDto() == null) {
            throw new BookNotFoundException("요청하신 도서를 찾을 수 없습니다. 도서가 삭제되었거나 존재하지 않는 번호일 수 있습니다.", bookId);
        }
        // 지은이 정보 조회
        bookDetailResponse.setWriter(bookRepository.selectWriter(bookId));
        if(bookDetailResponse.getWriter().isEmpty()) {
            throw new WriterNotFoundException("도서의 저자 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.", bookId);
        }
        // 출판사 정보 조회
        bookDetailResponse.setPublisher(bookRepository.selectPublisher(bookId));
        if(bookDetailResponse.getPublisher() == null) {
            throw new PublisherNotFoundException("출판사 정보를 확인할 수 없습니다. 정보가 누락되었거나 일시적인 오류일 수 있습니다.", bookId);
        }
        // 리뷰 정보 조회
        bookDetailResponse.setReview(reviewService.selectReview(bookId));
        // 리뷰가 0개일 경우 별점 조회
        bookDetailResponse.setRating(ZERO_RATING);
        // 별점 조회
        if(!bookDetailResponse.getReview().isEmpty()) {
            bookDetailResponse.setRating(reviewService.ratingReview(bookId));
        }
        // 카테고리 조회
        bookDetailResponse.setCategoryResponse(bookRepository.selectCategoryAll(bookId));

        return bookDetailResponse;
    }

    // 테스트용 insert
    @Override
    public int insert(BookDto book) throws Exception{
        return bookRepository.insert(book);
    }
}
