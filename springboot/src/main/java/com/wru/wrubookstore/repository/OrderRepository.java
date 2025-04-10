package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    /* select */
    // 주문코드를 통해 주문 한 개 조회
    OrderDto select(Integer orderId) throws Exception;
    // 회원코드를 통해 주문 리스트 조회
    List<OrderDto> selectList(Map<String, Object> map) throws Exception;
    // 조건에 따른 주문 내역 조회
    List<OrderHistoryRequest> selectListByOsc(Map<String, Object> map) throws Exception;
    // 주문 개수 조회
    int selectCnt(Integer userId) throws Exception;
    // 조건에 따른 주문 개수 조회
    int selectCntByOsc(Map<String, Object> map) throws Exception;
    // 주문상세조회 -> 주문상품 정보
    List<OrderBookRequest> selectOrderBook(Integer orderId) throws Exception;
    // 판매 순위별 상위 5권 bookId 조회 (판매량이 같을 경우 bookId가 큰 순서대로)
    List<Integer> selectBookIdInSalesRank() throws Exception;

    /* insert */
    // userId만 입력하여 주문 생성(orderId를 가져오기 위해 매개변수 OrderDto 사용)
    int insertOrder(OrderDto orderDto) throws Exception;
    // 주문_도서 생성
    int insertOrderBook(OrderBookDto orderBookDto) throws Exception;

    /* test */
    int insertOrderTest(OrderDto orderDto) throws Exception;
    int deleteAllOrdersTest() throws Exception;
    int deleteAllOrderBookTest() throws Exception;
}