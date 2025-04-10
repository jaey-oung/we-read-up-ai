package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.PaymentDto;

public interface PaymentRepository {

    /* select */
    // 주문번호로 결제 한 개 검색
    PaymentDto select(Integer orderId) throws Exception;

    /* insert */
    // 결제 생성
    int insert(PaymentDto paymentDto) throws Exception;
}
