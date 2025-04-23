package com.wru.wrubookstore;


import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.service.BookService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final BookService bookService;

    public GlobalModelAttributeAdvice(BookService bookService) {
        this.bookService = bookService;
    }

    /* 모든 화면에 대분류 메뉴 보이도록 가져오기 */
    @ModelAttribute("largeCategories")
    public List<CategoryResponse> addCategoryListToModel()  {

        List<CategoryResponse> largeCategories = new ArrayList<>();

        try {
            largeCategories = bookService.getAllLargeCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return largeCategories;
    }
}
