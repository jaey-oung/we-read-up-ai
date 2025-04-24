package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ajax")
public class CategoryAjaxController {

    private final BookService bookService;

    public CategoryAjaxController(BookService bookService) {
        this.bookService = bookService;
    }

    /* 대분류 카테고리 hover 시 중분류 카테고리 가져오기 */
    @GetMapping("/ctgM")
    public List<CategoryResponse> getMediumCategories(
            @RequestParam("cl_id") String categoryLargeId) {

        List<CategoryResponse> mediumCategories = new ArrayList<>();
        try {
            mediumCategories = bookService.getAllMediumCategories(categoryLargeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediumCategories;
    }

    /* 중분류 카테고리 hover 시 소분류 카테고리 가져오기 */
    @GetMapping("/ctgS")
    public List<CategoryResponse> getSmallCategories(
            @RequestParam("cm_id") String categoryMediumId) {

        List<CategoryResponse> smallCategories = new ArrayList<>();
        try {
            smallCategories = bookService.getAllSmallCategories(categoryMediumId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return smallCategories;
    }
}
