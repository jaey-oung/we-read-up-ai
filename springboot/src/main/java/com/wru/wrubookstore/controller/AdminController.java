package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminBookCreateData;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.helper.FileNameHelper;
import com.wru.wrubookstore.service.AdminService;
import com.wru.wrubookstore.service.BookService;
import com.wru.wrubookstore.validator.FileValidator;
import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    BookService bookService;
    AdminService adminService;
    FileValidator fileValidator;
    FileNameHelper fileNameHelper;

    AdminController(BookService bookService, AdminService adminService, FileValidator fileValidator, FileNameHelper fileNameHelper) {
        this.bookService = bookService;
        this.adminService = adminService;
        this.fileValidator = fileValidator;
        this.fileNameHelper = fileNameHelper;
    }

    // 업로드 경로
    private final String UPLOAD_DIR = "springboot/src/main/resources/static/img/book/";
    // 보여줄 상품 리스트
    private final int ALL_PRODUCT = 1;
    private final int SOLD_OUT = 0;
    private final int FOR_SALE = 2;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("image") MultipartFile formData) {
        // static/img/book/파일  - 파일이 저장되는 공간
        // 이미지 파일 검증
        fileValidator.validateImage(formData);

        try{
            String fileName = fileNameHelper.getFileName(formData);
            Path path = Paths.get(UPLOAD_DIR + fileName);
            // 디렉토리가 없으면 생성
            Files.createDirectories(path.getParent()); // 디렉토리 생성
            formData.transferTo(path);
            String imagePath = "/img/book/"+fileName;

            return imagePath;
        }catch(Exception e){
            e.printStackTrace();
            return "upload failed";
        }
    }

    @PostMapping("/bookCreate")
    @ResponseBody
    public String bookCreate(@RequestBody AdminResponse adminResponse) throws Exception{
        return adminService.createBook(adminResponse);
    }

    @GetMapping("/bookCategory")
    @ResponseBody
    public List<CategoryResponse> bookCategory(CategoryResponse categoryResponse) throws Exception{
        // 카테고리 정보를 조회
        return adminService.selectCategory(categoryResponse);
    }

    @GetMapping("/bookCreate")
    public String bookCreate(Model m) throws Exception{
        // 책 생성에 필요한 지은이 정보, 카테고리 정보, 출판사 정보를 조회
        AdminBookCreateData adminBookCreateData = adminService.selectBookCreateData();
        m.addAttribute("adminBookCreateData", adminBookCreateData);

        return "admin/admin-book-create";
    }


    @PostMapping("/bookDelete")
    @ResponseBody
    public String bookDelete(@RequestBody Integer[] bookId) throws Exception{
        return adminService.deleteMultipleBook(bookId);
    }

    @PostMapping("/bookAddQuantity")
    @ResponseBody
    public String bookAddQuantity(@RequestBody BookListResponse[] bookListResponse) throws Exception{
        return adminService.addQuantity(bookListResponse);
    }

    @GetMapping("/authorSearch")
    @ResponseBody
    public List<WriterDto> authorSearch(String keyword) throws Exception{
        return adminService.searchWriter(keyword);
    }

    @GetMapping("/publisherSearch")
    @ResponseBody
    public List<PublisherDto> publisherSearch(String keyword) throws Exception{
        return adminService.searchPublisher(keyword);
    }

    @GetMapping("/bookList")
    public String bookList(Model m, Integer page, Integer pageSize, Integer quantity, String searchWord){
        try{
            page = (page == null || page < 1) ? 1 : page;
            pageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;
            quantity = (quantity == null) ? ALL_PRODUCT : quantity;

            // 관리자용 등록된 상품 갯수
            int countAllByAdmin = bookService.countAllByAdmin();
            int countQuantityZeroByAdmin = bookService.countQuantityZeroByAdmin();
            int listCount = switch(quantity){
                // 모든상품
                case ALL_PRODUCT -> countAllByAdmin;
                // 판매 중지된 상품
                case SOLD_OUT -> countQuantityZeroByAdmin;
                // 판매 중인 상품
                case FOR_SALE -> countAllByAdmin - countQuantityZeroByAdmin;

                default -> ALL_PRODUCT;
            };

            // 페이징
            PageHandler pageHandler = new PageHandler(listCount, page, pageSize);
            List<BookDto> bookDto = adminService.getBookList(quantity, (page-1)*pageSize, pageSize, searchWord);

            // 상품 정보
            m.addAttribute("countAllByAdmin", countAllByAdmin);
            m.addAttribute("countQuantityZeroByAdmin", countQuantityZeroByAdmin);
            m.addAttribute("bookDto", bookDto);
            m.addAttribute("quantity", quantity);
            m.addAttribute("ph", pageHandler);

        } catch (Exception e){
            e.printStackTrace();
        }

        return "admin/admin-book-list";
    }

}
