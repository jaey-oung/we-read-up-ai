package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import com.wru.wrubookstore.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.*;

@Controller
public class BookController {
    private final BookService bookService;
    private final LikeService likeService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final UserService userService;

    BookController(BookService bookService, UserService userService,LikeService likeService, ReviewService reviewService, MemberService memberService) {
        this.bookService = bookService;
        this.likeService = likeService;
        this.reviewService = reviewService;
        this.memberService = memberService;
        this.userService = userService;

    }

    /* 메인 홈페이지 메뉴의 카테고리 클릭 시 도서 리스트 출력 */
    @GetMapping("/bookList")
    public String bookList(HomeSearchCondition sc,
                           @RequestParam("cl_id") String categoryLargeId,
                           @RequestParam("cm_id") String categoryMediumId,
                           @RequestParam("cs_id") String categorySmallId,
                           @RequestParam("path") String path,
                           Model model, HttpServletRequest request) {

        // 대/중/소분류 카테고리 id를 하나의 객체로 관리
        CategoryRequest categoryIds = new CategoryRequest(categoryLargeId, categoryMediumId, categorySmallId);

        try{
            // 해당 카테고리에 속한 도서 개수 구하기
            int count = bookService.getCntByCategoryIds(categoryIds);

            List<CompleteBookDto> list = new ArrayList<>();
            if (count != 0) {
                // 페이징, 정렬과 관련된 sc와 카테고리 id를 조합해 조건에 맞는 도서 리스트 반환
                BookFilterDto bookFilterDto = new BookFilterDto(sc, categoryIds);
                list = bookService.getAllCompleteBooks(bookFilterDto); // 책, 출판사, 저자 정보 포함
            }

            model.addAttribute("sc", sc);
            model.addAttribute("list", list);
            model.addAttribute("ph", new PageHandler(count, sc.getPage(), sc.getPageSize()));
            model.addAttribute("path", path);
            model.addAttribute("url", request.getRequestURI()); // 페이징 시 해당 url 정보 전달
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "book/book-list";
    }

    /* 검색 창에서 통합검색, 저자명, 도서명 옵션으로 키워드 검색 */
    @GetMapping("/search")
    public String search(HomeSearchCondition sc, Model model, HttpServletRequest request) {

        try {
            // 검색 결과 개수 반환
            int count = bookService.getCntBySearchCondition(sc);

            List<CompleteBookDto> list = new ArrayList<>();
            if (count != 0) {
                // 검색 옵션과 키워드에 맞는 도서 리스트 반환
                list = bookService.getAllCompleteBooks(sc);
            }

            model.addAttribute("sc", sc);
            model.addAttribute("list", list);
            model.addAttribute("ph", new PageHandler(count, sc.getPage(), sc.getPageSize()));
            model.addAttribute("uri", request.getRequestURI()); // 페이징 시 해당 uri 정보 전달
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "book/book-list";
    }

    @GetMapping("/bookDetail")
    public String bookDetail(@SessionAttribute(value = "userId", required = false) Integer userId ,Integer bookId, Integer page, String category, Integer pageSize, Model m) {
        try{
            int isLikeUser = 0;
            int memberId = 0;
            int isMember = 0;

            if(userId != null){
                System.out.println("userId = " + userId);
                MemberDto memberDto = memberService.selectMember(userId);
                System.out.println("bookDetail//memberDto = " + memberDto);
                UserDto userDto = userService.selectUser(userId);
                System.out.println("userDto = " + userDto);

                if(userDto.getIsMember()){
                    isMember = 1;
                }
                if (memberDto != null) {
                    memberId = memberDto.getMemberId();
                    // 좋아요 누른 회원의 정보 조회
                    LikeDto likeDto = new LikeDto(bookId, memberId);
                    // 0이면 좋아요 안누른 유저, 1이면 좋아요 누른 유저
                    isLikeUser = likeService.selectLikeMember(likeDto);
                }
            }

            // 책 정보 조회
            BookDto bookDto = bookService.select(bookId);
            // 지은이들 조회
            List<String> writer = bookService.selectWriter(bookId);
            // 출판사 조회
            String publisher = bookService.selectPublisher(bookId);
            // 리뷰들 조회
            List<ReviewListResponse> review = reviewService.selectReview(bookId);
            // 리뷰가 있는지 없는지 확인
            int reviewCnt = reviewService.countReview(bookId);
            CategoryResponse categoryResponse = bookService.selectCategorySM(bookId);
            categoryResponse.setCategoryLargeName(bookService.selectCategoryL(categoryResponse).getCategoryLargeName());
            // 리뷰 점수 조회
            double rating;
            if(reviewCnt == 0){
                rating = 0;
            } else{
                rating = reviewService.ratingReview(bookId);
            }

            System.out.println("rating = " + rating);

            System.out.println("여기야!!//categoryResponse = " + categoryResponse);

            m.addAttribute("review", review);
            m.addAttribute("bookDto", bookDto);
            m.addAttribute("writer", writer);
            m.addAttribute("publisher", publisher);
            m.addAttribute("reviewCnt", reviewCnt);
            m.addAttribute("isLikeUser", isLikeUser);
            m.addAttribute("memberId", memberId);
            m.addAttribute("category", categoryResponse);
            m.addAttribute("rating", rating);
            m.addAttribute("isMember", isMember);
            m.addAttribute("userId", userId);
            System.out.println("bookDetail//isLikeUser = " + isLikeUser);


            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        } catch(Exception e){
            e.printStackTrace();
        }


        return "book/book-detail";
    }
}
