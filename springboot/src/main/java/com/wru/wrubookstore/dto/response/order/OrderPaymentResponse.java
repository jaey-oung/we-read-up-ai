package com.wru.wrubookstore.dto.response.order;

import com.wru.wrubookstore.dto.DeliveryDto;
import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class OrderPaymentResponse {

    private OrderDto orderDto;
    private List<OrderBookDto> orderBookDtoList;
    private DeliveryDto deliveryDto;
    private PaymentDto paymentDto;
}
