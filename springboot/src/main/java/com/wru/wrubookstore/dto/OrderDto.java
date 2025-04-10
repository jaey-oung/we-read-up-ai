package com.wru.wrubookstore.dto;

import com.wru.wrubookstore.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class OrderDto {

    private Integer orderId;
    private Integer userId;
    private String userName;
    private OrderStatus statusId;
    private Date regDate;
    private Date endDate;

    public OrderDto() {}

    public OrderDto(Integer userId, OrderStatus statusId, Date regDate, Date endDate) {
        this.userId = userId;
        this.statusId = statusId;
        this.regDate = regDate;
        this.endDate = endDate;
    }
}
