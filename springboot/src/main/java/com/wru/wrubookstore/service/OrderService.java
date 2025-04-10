package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.OrderSearchCondition;
import com.wru.wrubookstore.dto.request.order.OrderDetailRequest;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;
import com.wru.wrubookstore.dto.request.order.OrderPaymentRequest;
import com.wru.wrubookstore.dto.response.order.OrderPaymentResponse;

import java.util.List;

public interface OrderService {

    // 주문 상태, 날짜, 페이징에 따른 게시물 리스트
    List<OrderHistoryRequest> selectListByOsc(Integer userId, OrderSearchCondition osc) throws Exception;
    // 주문 상태, 날짜에 따른 게시물 개수
    int selectCntByOsc(Integer userId, OrderSearchCondition osc) throws Exception;
    // 주문상세 조회
    OrderDetailRequest selectOrderDetail(Integer orderId) throws Exception;
    // 주문,결제 정보 조회
    OrderPaymentRequest selectOrderPayment(Integer userId, OrderPaymentRequest orderPaymentRequest) throws Exception;
    // 주문,결제 접수 -> 주문,주문_도서,배송,결제,회원 마일리지 추가
    void processOrder(OrderPaymentResponse orderPaymentResponse) throws Exception;
}
