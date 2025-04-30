package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.DeliveryDto;

public interface DeliveryRepository {

    /* select */
    // 주문번호로 배송 한 개 검색
    DeliveryDto select(Integer orderId) throws Exception;

    /* insert */
    // 배송 생성
    int insert(DeliveryDto deliveryDto) throws Exception;

    /* delete */
    // 배송 전체 삭제
    int deleteAll() throws Exception;
}
