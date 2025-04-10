package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.OrderStatus;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.RankedBookDto;
import com.wru.wrubookstore.repository.BookRepository;
import com.wru.wrubookstore.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceImplTest {

    @Autowired
    BookRepository book;
    @Autowired
    BookService bookService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void insert() throws Exception {
        BookDto bookDto = new BookDto(
                "pub_1",
                "cs_1",
                "텐서플로",
                "김",
                1000,
                new BigDecimal(0.01),
                170,
                1200,
                new Date(),
                new Date(),
                "1장 컴퓨터는 데이터에서 배운다",
                "코드 실행만으로는 머신 러닝과 딥러닝을 충분히 이해할 수 없다. 머신 러닝과 딥러닝을 제대로 이해하고 싶다면 코드 외에도 관련 이론과 알고리즘의 뒤편에 있는 수학 개념을 알아야 한다. 이 책은 이해를 돕는 개념 설명, 머신 러닝과 딥러닝 핵심 알고리즘의 작동 방식과 사용 방법, 그 밑바탕이 되는 수학, 실용적인 예제, 빠지기 쉬운 함정을 피하는 방법까지 이론과 코드를 균형 있게 설명한다.",
                1111111111,
                100,
                "133*200",
                420,
                300,
                "https://image.aladin.co.kr/product/32875/63/cover500/k562936112_2.jpg");
        book.insert(bookDto);
    }

    @DisplayName("판매 순위별 상위 5권 조회")
    @Test
    @Transactional
    public void getWeeklyRankingTest() throws Exception {
        // 주문 데이터 전체 삭제
        orderRepository.deleteAllOrdersTest();
        orderRepository.deleteAllOrderBookTest();

        // 배송 완료된 주문 데이터 삽입
        OrderDto order1 = new OrderDto(1, OrderStatus.DS3, new Date(), new Date());
        OrderDto order2 = new OrderDto(2, OrderStatus.DS3, new Date(), new Date());
        OrderDto order3 = new OrderDto(3, OrderStatus.DS3, new Date(), new Date());
        OrderDto order4 = new OrderDto(4, OrderStatus.DS3, new Date(), new Date());
        OrderDto order5 = new OrderDto(5, OrderStatus.DS3, new Date(), new Date());
        OrderDto order6 = new OrderDto(6, OrderStatus.DS3, new Date(), new Date());
        OrderDto order7 = new OrderDto(7, OrderStatus.DS3, new Date(), new Date());

        // orders 테이블에 데이터 삽입 (end_date 확인)
        orderRepository.insertOrderTest(order1);
        orderRepository.insertOrderTest(order2);
        orderRepository.insertOrderTest(order3);
        orderRepository.insertOrderTest(order4);
        orderRepository.insertOrderTest(order5);
        orderRepository.insertOrderTest(order6);
        orderRepository.insertOrderTest(order7);

        // order_book 테이블에 데이터 삽입 (실제 구매 도서 확인)
        orderRepository.insertOrderBook(new OrderBookDto(order1.getOrderId(), 1, 1, 10000));
        orderRepository.insertOrderBook(new OrderBookDto(order2.getOrderId(), 1, 3, 30000));
        orderRepository.insertOrderBook(new OrderBookDto(order3.getOrderId(), 2, 1, 10000));
        orderRepository.insertOrderBook(new OrderBookDto(order4.getOrderId(), 3, 1, 10000));
        orderRepository.insertOrderBook(new OrderBookDto(order5.getOrderId(), 4, 2, 20000));
        orderRepository.insertOrderBook(new OrderBookDto(order6.getOrderId(), 5, 2, 20000));
        orderRepository.insertOrderBook(new OrderBookDto(order7.getOrderId(), 6, 6, 60000));

        // 결과 확인
        // 6-6 (1)
        // 1-4 (2)
        // 5-2 (3)
        // 4-2 (4)
        // 3-1 (5)
        // 2-1
        List<RankedBookDto> rankedBooks = bookService.getWeeklyRanking();
        List<Integer> rankedBookIds = new ArrayList<>();
        rankedBooks.forEach(rankedBook -> {
            rankedBookIds.add(rankedBook.getBookAndCategory().getBookId());
        });
        assertEquals(Arrays.asList(6, 1, 5, 4, 3), rankedBookIds);
    }
}