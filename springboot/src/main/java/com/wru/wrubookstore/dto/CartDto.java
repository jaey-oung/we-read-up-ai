package com.wru.wrubookstore.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
// 하나의 장바구니는 사용자와 도서 조합 (중복 도서 방지)
@EqualsAndHashCode(of = {"bookId", "userId"})
public class CartDto {
    private Integer cartId;         // 장바구니 코드
    private Integer bookId;         // 도서 코드
    private Integer userId;         // 사용자 코드
    private int quantity;           // 장바구니 도서 수량
    private int price;              // 장바구니 주문 금액
    private LocalDate regDate;      // 장바구니 담은 날짜

    public CartDto() {}
    public CartDto(Integer bookId, Integer userId, int quantity, int price) {
        this.bookId = bookId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
    }
}
