package com.wru.wrubookstore.dto.request.order;

import com.wru.wrubookstore.dto.AddressDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class OrderPaymentRequest {

    private AddressDto addressDto;  // 배송지
    private List<OrderBookRequest> orderBookRequestList;
    private int mileage;

    public OrderPaymentRequest() {

    }

    public OrderPaymentRequest(AddressDto addressDto, List<OrderBookRequest> orderBookRequestList, int mileage) {
        this.addressDto = addressDto;
        this.orderBookRequestList = orderBookRequestList;
        this.mileage = mileage;
    }
}
