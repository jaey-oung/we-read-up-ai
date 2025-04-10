package com.wru.wrubookstore.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MyPageRequest {

    private String image;
    private String membershipName;
    private int mileage;
    private int couponCnt;
    private int orderCnt;
    private int orderDs1Cnt;
    private int orderDs2Cnt;
    private int orderDs3Cnt;
    private int exchangeCnt;
    private int refundCnt;

    public MyPageRequest() {

    }

    public MyPageRequest(int orderCnt, int orderDs1Cnt, int orderDs2Cnt, int orderDs3Cnt, int exchangeCnt, int refundCnt) {
        this.orderCnt = orderCnt;
        this.orderDs1Cnt = orderDs1Cnt;
        this.orderDs2Cnt = orderDs2Cnt;
        this.orderDs3Cnt = orderDs3Cnt;
        this.exchangeCnt = exchangeCnt;
        this.refundCnt = refundCnt;
    }

    public MyPageRequest(String image, String membershipName, int mileage, int couponCnt, int orderCnt, int orderDs1Cnt, int orderDs2Cnt, int orderDs3Cnt, int exchangeCnt, int refundCnt) {
        this.image = image;
        this.membershipName = membershipName;
        this.mileage = mileage;
        this.couponCnt = couponCnt;
        this.orderCnt = orderCnt;
        this.orderDs1Cnt = orderDs1Cnt;
        this.orderDs2Cnt = orderDs2Cnt;
        this.orderDs3Cnt = orderDs3Cnt;
        this.exchangeCnt = exchangeCnt;
        this.refundCnt = refundCnt;
    }
}
