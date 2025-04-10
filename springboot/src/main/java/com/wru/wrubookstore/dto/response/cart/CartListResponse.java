package com.wru.wrubookstore.dto.response.cart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartListResponse {
    private Integer cartId;             // 장바구니 코드
    private Integer bookId;             // 도서 코드
    private Integer userId;             // 사용자 코드
    private int quantity;               // 장바구니 도서 수량
    private int price;                  // 장바구니 주문 금액
    // 도서 정보
    private String name;                // 도서 명
    private int originalPrice;          // 도서 정가
    private BigDecimal discountPercent; // 도서 할인율
    private int salePrice;              // 도서 판매가
    private String image;               // 도서

    public CartListResponse() {}

    public CartListResponse(Integer cartId, Integer bookId, Integer userId, int quantity, int price, String name,
                            int originalPrice, BigDecimal discountPercent, int salePrice, String image) {
        this.cartId = cartId;
        this.bookId = bookId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discountPercent = discountPercent;
        this.salePrice = salePrice;
        this.image = image;
    }
}
