package com.wru.wrubookstore;

import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<RankedBookDto> rankedBooks = new ArrayList<>();
        try {
            /* 주간랭킹 - 판매량 기준 상위 5권 조회 */
            rankedBooks = bookService.getWeeklyRanking();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("rankedBooks", rankedBooks);

        return "home";
    }
}
