package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.domain.SearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.dto.response.publisher.PublisherListResponse;
import com.wru.wrubookstore.dto.response.review.ReviewListResponse;
import com.wru.wrubookstore.dto.response.writer.WriterListResponse;
import com.wru.wrubookstore.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.Writer;
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
    public String bookList(MainSearchCondition sc, Model model, HttpServletRequest request, Integer sort) {

        try{
            if(sort == null){
                sort = 0;
            }
            // 도서 테이블에서 특정 카테고리에 속한 도서 개수 파악
            int count = bookService.selectByCategoryCnt(sc.getCategory());
            List<CategoryDto> list = new ArrayList<>();

            // 개수가 0이면 카테고리만 조인한 테이블에서 카테고리 정보만 반환
            if (count == 0) {
                CategoryDto categoryInfo = bookService.selectCategoryInfo(sc.getCategory());
                model.addAttribute("category", categoryInfo);
            } else if (count > 0) {
                // 개수가 1 이상이면 도서 테이블에서 특정 카테고리에 속한 도서 개수 및 도서 리스트, 카테고리 정보 반환
                if(sort == 0){
                    list = bookService.selectByCategory(sc);
                }
                else if(sort == 1){
                    list = bookService.selectByCategory2(sc);
                }
                else if(sort == 2){
                    list = bookService.selectByCategory3(sc);
                }
            } else {
                throw new Exception("잘못된 도서 개수입니다.");
            }

            model.addAttribute("sc", sc);
            model.addAttribute("list", list);
            model.addAttribute("ph", new PageHandler(count, sc.getPage(), sc.getPageSize()));
            model.addAttribute("uri", request.getRequestURI()); // 페이징 시 해당 uri 정보 전달
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "book/book-list";
    }


    /* 검색 창에 통합검색, 저자명, 도서명 옵션으로 키워드 검색 */
    @GetMapping("/search")
    public String search(MainSearchCondition sc, Model model, HttpServletRequest request, Integer sort) {

        try {
            if(sort == null){
                sort = 0;
            }
            // scDto에서 어떤 옵션을 통한 검색인지 가져오기
            String option = sc.getOption();
            // 검색 결과 개수 반환
            int count = bookService.selectSearchCnt(sc);
            // 옵션과 키워드를 통한 도서 리스트 반환
            List<BookDto> list = new ArrayList<>();

            System.out.println("sort = " + sort);

            // 리스트에 담겨있는 BookDto 정보를
            // 1. 최신순
            if(sort == 0){
                list = switch (option) {
                    case "all" -> bookService.searchByAll(sc);
                    case "title" ->  bookService.searchByTitle(sc);
                    case "writer" -> bookService.searchByWriter(sc);
                    default -> throw new Exception("잘못된 옵션입니다.");
                };
            }
            // 2. 낮은 가격순
            else if(sort == 1){
                list = switch (option) {
                    case "all" -> bookService.searchByAll2(sc);
                    case "title" ->  bookService.searchByTitle2(sc);
                    case "writer" -> bookService.searchByWriter2(sc);
                    default -> throw new Exception("잘못된 옵션입니다.");
                };
            }
            // 3. 높은 가격순
            else if(sort == 2){
                list = switch (option) {
                    case "all" -> bookService.searchByAll3(sc);
                    case "title" ->  bookService.searchByTitle3(sc);
                    case "writer" -> bookService.searchByWriter3(sc);
                    default -> throw new Exception("잘못된 옵션입니다.");
                };
            }



            List<List<WriterListResponse>> writerListResponse = new ArrayList<>();
            Map<String,String> publisherListResponse = new HashMap<>();

            for(BookDto dto : list){
                writerListResponse.add(bookService.selectWriterName(dto.getBookId()));
                publisherListResponse.put(bookService.selectPublisherName(dto.getPublisherId()).getPublisherId(),bookService.selectPublisherName(dto.getPublisherId()).getName());
            }

//            List<PublisherListResponse> publisher = new ArrayList<>(publisherListResponse);

            System.out.println("출판사 여기//publisherListResponse = " + publisherListResponse);
            System.out.println("지은이여기//writerListResponse = " + writerListResponse);

            PageHandler pageHandler = new PageHandler(count, sc.getPage(), sc.getPageSize());
            model.addAttribute("publisherListResponse", publisherListResponse);
            model.addAttribute("writerListResponse", writerListResponse);
            model.addAttribute("sc", sc);
            model.addAttribute("list", list);
            model.addAttribute("ph", pageHandler);
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
