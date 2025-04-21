package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.domain.SearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.response.book.BookDetailResponse;
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
import org.springframework.web.bind.annotation.*;

import java.io.Writer;
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


    // 상품 상세 페이지
    @GetMapping("/bookDetail")
    public String bookDetail(@SessionAttribute(value = "userId", required = false) Integer userId, Integer bookId, Model m) throws Exception {
        // 상품 상세 정보(책, 지은이, 출판사, 리뷰, 좋아요, 카테고리, 별점)
        BookDetailResponse bookDetailResponse = bookService.select(bookId, userId);

        m.addAttribute("bookDetailResponse", bookDetailResponse);

        return "book/book-detail";
    }
}
