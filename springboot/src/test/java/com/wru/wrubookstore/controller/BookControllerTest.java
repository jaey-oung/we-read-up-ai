//package com.wru.wrubookstore.controller;
//
//import com.wru.wrubookstore.dto.BookDto;
//import com.wru.wrubookstore.repository.BookRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class BookControllerTest {
//    @Autowired
//    BookRepository book;
//
//    @Test
//    public void insert() throws Exception {
//        BookDto bookDto = new BookDto(
//                "pub_1",
//                "cs_1",
//                "텐서플로",
//                "김",
//                1000,
//                new BigDecimal(0.01),
//                170,
//                1200,
//                new Date(),
//                new Date(),
//                "1장 컴퓨터는 데이터에서 배운다",
//                "코드 실행만으로는 머신 러닝과 딥러닝을 충분히 이해할 수 없다. 머신 러닝과 딥러닝을 제대로 이해하고 싶다면 코드 외에도 관련 이론과 알고리즘의 뒤편에 있는 수학 개념을 알아야 한다. 이 책은 이해를 돕는 개념 설명, 머신 러닝과 딥러닝 핵심 알고리즘의 작동 방식과 사용 방법, 그 밑바탕이 되는 수학, 실용적인 예제, 빠지기 쉬운 함정을 피하는 방법까지 이론과 코드를 균형 있게 설명한다.",
//                1111111111,
//                100,
//                "133*200",
//                420,
//                300,
//                "https://image.aladin.co.kr/product/32875/63/cover500/k562936112_2.jpg");
//        book.insert(bookDto);
//    }
//
//}