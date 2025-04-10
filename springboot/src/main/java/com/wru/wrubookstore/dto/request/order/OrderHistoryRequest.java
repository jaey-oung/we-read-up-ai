package com.wru.wrubookstore.dto.request.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class OrderHistoryRequest {

    private Date regDate;
    private Integer orderId;
    private String userName;
    private String bookName;
    private int bookTypeCount;
    private int totalBookCount;
    private int totalPrice;

    public OrderHistoryRequest() {

    }

    public OrderHistoryRequest(Date regDate, Integer orderId, String userName, String bookName, int bookTypeCount, int totalBookCount, int totalPrice) {
        this.regDate = regDate;
        this.orderId = orderId;
        this.userName = userName;
        this.bookName = bookName;
        this.bookTypeCount = bookTypeCount;
        this.totalBookCount = totalBookCount;
        this.totalPrice = totalPrice;
    }
}
