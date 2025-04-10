package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.domain.OrderStatus;
import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryImplTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    @Transactional
    public void insertOrderTest() throws Exception {
        Integer userId = 3;
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);

        int insertCnt = orderRepository.insertOrder(orderDto);

        System.out.println("orderDto.getOrderId() = " + orderDto.getOrderId());
        assertEquals(insertCnt, 1);
    }

    @DisplayName("판매 순위별 상위 5권 bookId 조회")
    @Test
    @Transactional
    public void selectBookIdInSalesRankTest() throws Exception {
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
        List<Integer> rankedBooks = orderRepository.selectBookIdInSalesRank();

        // 리스트가 비어있지 않고 개수가 5인지 확인
        assertNotNull(rankedBooks);
        assertEquals(5, rankedBooks.size());
        assertEquals(Arrays.asList(6, 1, 5, 4, 3), rankedBooks);
    }
}