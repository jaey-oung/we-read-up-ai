package com.wru.wrubookstore.dto.request.order;

import com.wru.wrubookstore.dto.DeliveryDto;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class OrderDetailRequest {

    private OrderDto orderDto;
    private PaymentDto paymentDto;
    private List<OrderBookRequest> orderBookRequestList;
    private DeliveryDto deliveryDto;

    public OrderDetailRequest() {

    }

    public OrderDetailRequest(OrderDto orderDto, PaymentDto paymentDto, List<OrderBookRequest> orderBookRequestList, DeliveryDto deliveryDto) {
        this.orderDto = orderDto;
        this.paymentDto = paymentDto;
        this.orderBookRequestList = orderBookRequestList;
        this.deliveryDto = deliveryDto;
    }
}
