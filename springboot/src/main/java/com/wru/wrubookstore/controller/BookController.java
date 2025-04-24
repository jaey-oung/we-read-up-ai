package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.BookFilterDto;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import com.wru.wrubookstore.dto.response.book.BookDetailResponse;
import com.wru.wrubookstore.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
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

    // 상품 상세 페이지
    @GetMapping("/bookDetail")
    public String bookDetail(@SessionAttribute(value = "userId", required = false) Integer userId, Integer bookId, Model m) throws Exception {
        // 상품 상세 정보(책, 지은이, 출판사, 리뷰, 좋아요, 카테고리, 별점)
        BookDetailResponse bookDetailResponse = bookService.select(bookId, userId);

        m.addAttribute("bookDetailResponse", bookDetailResponse);

        return "book/book-detail";
    }
}
