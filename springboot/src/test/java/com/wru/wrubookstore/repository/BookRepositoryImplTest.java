package com.wru.wrubookstore.repository;


import com.wru.wrubookstore.domain.MainSearchCondition;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.CategoryDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BookRepositoryImplTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Transactional
    public void select() throws Exception {
        for (int i = 1; i < 250; i++) {
            BookDto bookDto = new BookDto("pub_1","cs_1", ""+i, "김", 1000, new BigDecimal(0.01),170,1200, new Date(), new Date(), "목차", "내용", 1111111111, 100, "133*200", 420, 300,"https://image.aladin.co.kr/product/32875/63/cover500/k562936112_2.jpg");
            bookRepository.insert(bookDto);
        }
    }

    @Test
    @Transactional
    public void select2() throws Exception {
        List<String> writer = bookRepository.selectWriter(3);
        System.out.println("writer = " + writer);
    }

    @DisplayName("카테고리만 조인한 테이블에서 카테고리 정보 조회")
    @Test
    @Transactional
    public void selectCategoryInfoTest() throws Exception {
        // 대분류 'cl_1'로 카테고리 정보 조회 (국내)
        CategoryDto categoryDto = bookRepository.selectCategoryInfo("cl_1");

        assertNotNull(categoryDto);
        assertEquals("cl", categoryDto.getCategoryType());
        assertEquals("cl_1", categoryDto.getCategoryLargeId());
        assertEquals("국내", categoryDto.getCategoryLargeName());

        // 중분류 'cm_1'로 카테고리 정보 조회 (국내>건강)
        categoryDto = bookRepository.selectCategoryInfo("cm_1");

        assertNotNull(categoryDto);
        assertEquals("cm", categoryDto.getCategoryType());
        assertEquals("cl_1", categoryDto.getCategoryLargeId());
        assertEquals("국내", categoryDto.getCategoryLargeName());
        assertEquals("cm_1", categoryDto.getCategoryMediumId());
        assertEquals("건강", categoryDto.getCategoryMediumName());

        // 소분류 'cs_1'로 카테고리 정보 조회 (국내>건강>골프)
        categoryDto = bookRepository.selectCategoryInfo("cs_1");

        assertNotNull(categoryDto);
        assertEquals("cs", categoryDto.getCategoryType());
        assertEquals("cl_1", categoryDto.getCategoryLargeId());
        assertEquals("국내", categoryDto.getCategoryLargeName());
        assertEquals("cm_1", categoryDto.getCategoryMediumId());
        assertEquals("건강", categoryDto.getCategoryMediumName());
        assertEquals("cs_1", categoryDto.getCategorySmallId());
        assertEquals("골프", categoryDto.getCategorySmallName());

        // 카테고리 테이블에 존재하지 않는 카테고리로 조회 - 코드 오류
        assertThrows(IllegalArgumentException.class, () -> bookRepository.selectCategoryInfo("cz_1"),
                "유효한 카테고리가 아닙니다.");

        // 카테고리 테이블에 존재하지 않는 카테고리로 조회 - 도메인 오류
        assertThrows(IllegalArgumentException.class, () -> bookRepository.selectCategoryInfo("cl_10"),
                "유효한 카테고리가 아닙니다.");
    }

    @DisplayName("도서 테이블에서 특정 카테고리에 속한 책들의 수 조회")
    @Test
    @Transactional
    public void selectByCategoryCntTest() throws Exception {
        // 등록된 도서가 없는 카테고리를 조회
        int count = bookRepository.selectByCategoryCnt("cs_18");
        assertEquals(0, count);

        // 해당 카테고리에 새로운 도서를 추가하고 조회
        bookRepository.insert(new BookDto("pub_1","cs_18", "test", "tester", 10000, new BigDecimal(0.01),170,1200, new Date(), new Date(), "목차", "내용", 1111111111, 100, "133*200", 420, 300,"https://image.aladin.co.kr/product/32875/63/cover500/k562936112_2.jpg"));
        count = bookRepository.selectByCategoryCnt("cs_18");
        assertEquals(1, count);
    }

    @DisplayName("특정 카테고리에 속한 책들의 정보 조회")
    @Test
    @Transactional
    public void selectByCategoryTest() throws Exception {
        // 특정 카테고리에 속한 도서 조회
        MainSearchCondition sc = new MainSearchCondition(1, 8, "cs_2");
        List<CategoryDto> books = bookRepository.selectByCategory(sc);
        assertEquals(3, books.size());
        for (CategoryDto categoryDto : books) {
            assertEquals("cs_2", categoryDto.getCategorySmallId());
        }
    }

    @DisplayName("도서 제목과 저자 이름으로 통합 검색")
    @Test
    @Transactional
    public void searchByAllTest() throws Exception {
        // 통합 검색 시 해당 키워드 정보를 포함하는 도서 조회
        MainSearchCondition sc = new MainSearchCondition(1, 8, "남궁성", "all");
        List<BookDto> books = bookRepository.searchByAll(sc);
        assertTrue(books.size() <= (bookRepository.searchByTitle(sc).size() + bookRepository.searchByWriter(sc).size()));
    }

    @DisplayName("도서 제목으로 검색")
    @Test
    @Transactional
    public void searchByTitleTest() throws Exception {
        // 도서 제목에 '정석' 키워드가 포함된 도서 조회 (2권)
        MainSearchCondition sc = new MainSearchCondition(1, 8, "정석", "title");
        List<BookDto> books = bookRepository.searchByTitle(sc);
        assertEquals(2, books.size());
        for (BookDto book : books) {
            assertTrue(book.getName().contains("정석"));
        }
    }

    @DisplayName("저자 이름으로 검색")
    @Test
    @Transactional
    public void searchByWriterTest() throws Exception {
        // 저자 이름에 '남궁' 키워드가 포함된 도서 조회 (2권)
        MainSearchCondition sc = new MainSearchCondition(1, 8, "남궁", "writer");
        List<BookDto> books = bookRepository.searchByWriter(sc);
        assertEquals(2, books.size());
        for (BookDto book : books) {
            List<String> writer_books = bookRepository.selectWriter(book.getBookId());
            assertTrue(writer_books.stream().anyMatch(writer -> writer.contains("남궁")));
        }
    }

    @DisplayName("검색 결과로 반환된 도서 개수 조회")
    @Test
    @Transactional
    public void selectSearchCntTest() throws Exception {
        // 해당 검색어를 포함하는 도서 조회 - 통합 검색
        MainSearchCondition sc = new MainSearchCondition(1, 8, "정석", "all");
        int count = bookRepository.selectSearchCnt(sc);
        assertEquals(2, count);

        // 해당 검색어를 포함하는 도서 조회 - 도서 제목
        sc = new MainSearchCondition(1, 8, "정석", "title");
        count = bookRepository.selectSearchCnt(sc);
        assertEquals(2, count);

        // 해당 검색어를 포함하는 도서 조회 - 저자 이름
        sc = new MainSearchCondition(1, 8, "정석", "writer");
        count = bookRepository.selectSearchCnt(sc);
        assertEquals(0, count);

        // 유효하지 않은 키워드로 조회
        sc = new MainSearchCondition(1, 8, "invalid%k@eyw1ord", "all");
        count = bookRepository.selectSearchCnt(sc);
        assertEquals(0, count);
    }
}