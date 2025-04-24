//package com.wru.wrubookstore.repository;
//
//import com.wru.wrubookstore.dto.PaymentDto;
//import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PaymentRepositoryImplTest {
//
//    @Autowired
//    PaymentRepository paymentRepository;
//    @Autowired
//    OrderRepository orderRepository;
//
//    Integer orderId = 1;
//
//    @Test
//    public void selectTest() throws Exception {
//
//        PaymentDto paymentDto = paymentRepository.select(orderId);
//
//        assertEquals(paymentDto.getPaymentId(), 1);
//        assertEquals(paymentDto.getStatusId(), "PS2");
//        assertEquals(paymentDto.getPaymentMethod(), "신용카드");
//        assertEquals(paymentDto.getActualPrice(), 17820);
//    }
//
//    @Test
//    @Transactional
//    public void insertTest() throws Exception {
//        List<OrderBookRequest> orderBookRequestList = orderRepository.selectOrderBook(orderId);
//        String paymentMethod = "위리페이";
//        int totalPrice = 0;
//        for (OrderBookRequest orderBookRequest : orderBookRequestList) {
//            totalPrice += orderBookRequest.getOrderPrice();
//        }
//        int mileageDiscount = 1500;
//        int actualPrice = totalPrice - mileageDiscount;
//
//        PaymentDto paymentDto = new PaymentDto(orderId, paymentMethod, totalPrice, mileageDiscount, actualPrice);
//
//        int insertCnt = paymentRepository.insert(paymentDto);
//
//        assertEquals(insertCnt, 1);
//    }
//}