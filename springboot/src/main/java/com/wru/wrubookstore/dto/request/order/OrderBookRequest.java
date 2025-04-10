package com.wru.wrubookstore.dto.request.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderBookRequest {

    private Integer cartId;
    private Integer bookId;
    private String image;   // 책 이미지
    private String name;    // 책 이름
    private int orderPrice;  // 주문 가격
    private int quantity;   // 주문 수량

    public OrderBookRequest() {

    }

    public OrderBookRequest(Integer cartId, Integer bookId, String image, String name, int orderPrice, int quantity) {
        this.cartId = cartId;
        this.bookId = bookId;
        this.image = image;
        this.name = name;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }
}