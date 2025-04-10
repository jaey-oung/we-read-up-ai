package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.service.AdminService;
import com.wru.wrubookstore.service.BookService;
import jakarta.validation.Valid;
import jdk.jfr.Category;
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

    AdminController(BookService bookService, AdminService adminService) {
        this.bookService = bookService;
        this.adminService = adminService;
    }

    // 업로드 경로
    private final String UPLOAD_DIR = "src/main/resources/static/img/book/";
    private final int NUM = 1;
    private final String WRT = "wrt_";
    private final String PUB = "pub_";
    private final String WB = "wb_";

    // 사업자 번호 포맷팅 메서드
    private String getFormatBizRegNo(String bizRegNo){
        if(!(bizRegNo.contains("-")) && bizRegNo.length() == 10){
            return String.format("%s-%s-%s",
                    bizRegNo.substring(0,3),
                    bizRegNo.substring(3,5),
                    bizRegNo.substring(5));
        }
        return bizRegNo;
    }
    // 핸드폰 번호 포맷팅 메서드
    private String getFormatPhoneNumber(String phoneNum){
        if(!(phoneNum.contains("-")) && phoneNum.length() == 11){
            return String.format("%s-%s-%s",
                    phoneNum.substring(0,3),
                    phoneNum.substring(3,7),
                    phoneNum.substring(7));
        }
        return phoneNum;
    }

    @PostMapping("/upload")
    @ResponseBody
    @Transactional
    public String upload(@RequestParam("image") MultipartFile formData) {

        // static/img/book/파일  - 파일이 저장되는 공간

        System.out.println("file = " + formData.getOriginalFilename());
        System.out.println("formData.getResource() = " + formData.getResource());
        System.out.println("formData.getName() = " + formData.getName());
        System.out.println("formData.getSize() = " + formData.getSize());

        if(formData.isEmpty()){
            System.out.println("formData is empty");
            return "formData is empty";
        }

        try{
            String fileName = System.currentTimeMillis()+"_"+formData.getOriginalFilename();

            Path path = Paths.get(UPLOAD_DIR + fileName);

            // 디렉토리가 없으면 생성
            Files.createDirectories(path.getParent()); // 디렉토리 생성

            System.out.println("path.getFileName().toString() = " + path.getFileName().toString());
            System.out.println("path.toString() = " + path.toString());

            formData.transferTo(path);

            String imagePath = "/img/book/"+fileName;
            System.out.println("imagePath = " + imagePath);

            return imagePath;
        }catch(Exception e){
            e.printStackTrace();
            return "upload failed";
        }
    }

    @PostMapping("/bookCreate")
    @Transactional
    @ResponseBody
    public String bookCreate(@RequestBody AdminResponse adminResponse){
        System.out.println("\n\nadmin//bookCreate//adminResponse = " + adminResponse);
        System.out.println("\n\nadmin//bookCreate//admin//adminResponse.getWriterDto() = " + adminResponse.getWriterDto());
        try {
            // 검증 뒤로 밀 예정
            // 지은이 등록용 - writer-book에 사용
            List<String> writerIdList = new ArrayList<>();
            // 출판사 등록용 - 책 등록에 사용
            String publisherId = "";
            // 책 등록용 - writer-book에 사용
            int bookId = 0;
            // writer-book 등록용
            String writerBookId = "";


            for (WriterDto dto : adminResponse.getWriterDto()) {
                System.out.println("\n\nadmin//dto = " + dto);
                // dto에 저장된 name과 nickname을 비교해서 중복을 비교
                WriterDto writerDto = adminService.selectWriterOne(dto);

                // 없으면 writer 추가
                if (writerDto == null) {
                    // dto에는 writer_id가 없어서 추가해줘야함
                    // 마지막 writer_id를 불러옴(ex:wrt_5)
                    // wrt_를 자르고 5만 남김
                    String writerCode = adminService.selectWriterId().substring(adminService.selectWriterId().lastIndexOf("_") + 1);
                    // 마지막 번호에 +1을 해서 저장
                    int writerIdNum = Integer.parseInt(writerCode) + NUM;
                    // 다시 이어붙임 wrt_6
                    String writerId = WRT + writerIdNum;

                    System.out.println("writerId = " + writerId);

                    // dto에 writerId셋팅
                    dto.setWriterId(writerId);

                    System.out.println("writer//dto = " + dto);

                    // 지은이 등록
                    adminService.insertWriter(dto);

                    // wrt_6 담아둠 - 추후 writer_book에 넣을 예정
                    writerIdList.add(writerId);
                } else {
                    System.out.println("else//writerDto.getWriterId() = " + writerDto.getWriterId());
                    // wrt_? 담아둠 - 추후 writer_book에 넣을 예정
                    writerIdList.add(writerDto.getWriterId());
                }

            }

            System.out.println("지은이 담김//writerIdList = " + writerIdList);

            // 출판사
            for (PublisherDto dto : adminResponse.getPublisherDto()) {
                System.out.println("\n출판사//dto = " + dto);

                // 사업자 번호 000-00-00000
                // 핸드폰번호 010-0000-0000
                dto.setBizRegNo(getFormatBizRegNo(dto.getBizRegNo()));
                dto.setPhoneNum(getFormatPhoneNumber(dto.getPhoneNum()));
                System.out.println("\n출판사2//dto = " + dto);
                // 출판사 중복 체크
                PublisherDto publisherDto = adminService.selectPublisherOne(dto);

                // 출판사 정보가 없으면 새로 만들기
                if (publisherDto == null) {
                    // 마지막 publisher 코드 가져오기
                    String id = adminService.selectPublisherId().substring(adminService.selectPublisherId().indexOf("_") + 1);

                    int pubId = Integer.parseInt(id) + 1;

                    publisherId = PUB + pubId;
                    System.out.println("publisherId = " + publisherId);

                    // publisher_id 채워주기
                    dto.setPublisherId(publisherId);

                    System.out.println("\n출판 등록전//dto = " + dto);

                    adminService.insertPublisher(dto);
                } else {
                    // 출판사 정보가 있으면 출판사 코드만 가져오기
                    publisherId = publisherDto.getPublisherId();
                }

                System.out.println("\n출판사//publisherDto = " + publisherId);
            }


            // 책 등록
            for(BookDto dto : adminResponse.getBookDto()){
                // 책 중복 확인
                if(adminService.selectBook(dto) == null){
                    // isbn이 없는 책이라면

                    System.out.println("책등록//dto = " + dto);
                    dto.setPublisherId(publisherId);
                    dto.setDiscountPercent(dto.getDiscountPercent().multiply(new BigDecimal("0.01")));

                    System.out.println("\n책등록2//dto = " + dto);
                    adminService.insertBook(dto);

                    bookId = adminService.selectBook(dto).getBookId();
                    System.out.println("\n책등록3//bookId = " + bookId);
                } else {
                    return "isbn is already exist";
                }
            }

            // writer-book 등록
            for(String dto : writerIdList){
                System.out.println("dto = " + dto);
                // 지은이가 들어온 수만큼 증가
                int id = Integer.parseInt(adminService.selectWriterBookId().substring(adminService.selectWriterBookId().indexOf("_")+1)) + 1;

                writerBookId = WB + id;

                System.out.println("WBID//writerBookId = " + writerBookId);

                WriterBookDto writerBookDto = new WriterBookDto(writerBookId, dto, bookId);

                System.out.println("지은이-책등록//writerBookDto = " + writerBookDto);
                adminService.insertWriterBook(writerBookDto);
            }

        } catch(Exception e){
            e.printStackTrace();
            return "create fail";
        }

        return "success";
    }

    @PostMapping("/bookCategory")
    @ResponseBody
    public List<CategoryResponse> bookCategory(@RequestBody CategoryResponse[] categoryResponse, Model m){
        try{

            System.out.println("admin//categoryResponse = " + Arrays.toString(categoryResponse));
            for(CategoryResponse category : categoryResponse){
                List<CategoryResponse> list;
                if (category.getCategoryMediumId() == null) {
                    list = bookService.selectCategoryMedium(category);
                    System.out.println("\n\nadmin//Medium//List<CategoryResponse> = " + list.toString()+"\n\n");

                    return list;
                }

                list = bookService.selectCategorySmall(category);
                System.out.println("\n\nadmin//Small//List<CategoryResponse> = " + list.toString()+"\n\n");

                return list;
            }

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @GetMapping("/bookCreate")
    public String bookCategory(Model m){
        try{
            List<CategoryResponse> categoryResponse = bookService.selectCategoryLarge();
            List<WriterDto> writerDto = adminService.selectAllWriter();
            List<PublisherDto> publisherDto = adminService.selectAllPublisher();

//            System.out.println("\n\nadmin//Large//categoryResponse = " + Arrays.toString(categoryResponse.toArray())+"\n\n");
            m.addAttribute("cl", categoryResponse);
            m.addAttribute("authors", writerDto);
            m.addAttribute("publishers", publisherDto);
        } catch(Exception e){
            e.printStackTrace();
        }

        return "admin/admin-book-create";
    }

    @PostMapping("/bookDelete")
    @ResponseBody
    public String bookDelete(@RequestBody BookListResponse[] bookListResponse){
        try{
            System.out.println("admin//Arrays.toString(bookId) = " + Arrays.toString(bookListResponse));

            if(bookListResponse==null || bookListResponse.length==0){
                throw new Exception("No Such Element");
            }

            for(BookListResponse list : bookListResponse){
                bookService.deleteByAdmin(list);
            }


        }catch(Exception e){
            e.printStackTrace();
            return "fail";

        }
        return "success";
    }

    @PostMapping("/bookMod")
    @ResponseBody
    public String bookMod(@RequestBody BookListResponse[] bookListResponse, Integer page, Model m){
        try{
            System.out.println("admin//bookListResponse = " + Arrays.toString(bookListResponse));
            // 넘어온 정보가 없다면 에러
            if(bookListResponse == null || bookListResponse.length == 0){
                throw new Exception("No Such Element");
            }

            for(BookListResponse list : bookListResponse){
                bookService.updateByAdmin(list);
            }


        } catch(Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

    @GetMapping("/bookList")
    public String bookList(Model m, Integer page, Integer pageSize, Integer quantity, String name){
        try{
            if(page == null || page < 1){
                page = 1;
            }
            if(pageSize == null || pageSize < 1){
                pageSize = 5;
            }
            if(quantity == null){
                quantity = 1;
            }

            // 관리자용 등록된 상품 갯수
            int countAllByAdmin = bookService.countAllByAdmin();
            m.addAttribute("countAllByAdmin", countAllByAdmin);

            int countQuantityZeroByAdmin = bookService.countQuantityZeroByAdmin();
            m.addAttribute("countQuantityZeroByAdmin", countQuantityZeroByAdmin);

            // 페이징
            PageHandler pageHandler = new PageHandler(quantity == 1 ? countAllByAdmin : quantity == 0 ? countQuantityZeroByAdmin : countAllByAdmin-countQuantityZeroByAdmin, page, pageSize);
            m.addAttribute("ph", pageHandler);

            // 상품 정보
            Map map = new HashMap();
            map.put("offset", (page-1)*pageSize);
            map.put("limit", pageSize);

            List<BookDto> bookDto = new ArrayList<>();

            if(name != null && !name.isEmpty()){
                // 검색
                bookDto = adminService.searchBook(name);
            } else if(quantity == 1 && bookService.selectBook(map) != null){
                // 모두 보여주기
                bookDto = bookService.selectBook(map);
            } else if(quantity == 2 && adminService.selectZeroNotQuantityBook(map) != null){
                // 판매중인 상품만 보여주기
                bookDto = adminService.selectZeroNotQuantityBook(map);
            } else if(quantity == 0 && adminService.selectZeroQuantityBook(map) != null){
                // 재고0인 상품만 보여주기
                bookDto = adminService.selectZeroQuantityBook(map);
            } else {
                // select가 null 인경우
                bookDto = null;
            }

            // 상품 정보
            m.addAttribute("bookDto", bookDto);
            m.addAttribute("quantity", quantity);

        } catch (Exception e){
            e.printStackTrace();
        }

        return "admin/admin-book-list";
    }

}
