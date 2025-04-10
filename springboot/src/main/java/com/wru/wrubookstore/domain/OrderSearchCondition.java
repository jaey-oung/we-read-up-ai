package com.wru.wrubookstore.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter @Setter
@ToString
public class OrderSearchCondition {

    private int page = 1;
    private int pageSize = 4;
    private int offset;
    private String startDate;
    private String endDate;
    private String statusId;

    public OrderSearchCondition() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        cal.add(Calendar.DAY_OF_WEEK, 1);

        this.startDate = df.format(new Date(cal.getTimeInMillis()));
        this.endDate = df.format(new Date());
        this.statusId = "A";
    }

    public OrderSearchCondition(String startDate, String endDate, String statusId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusId = statusId;
    }

    public int setOffset() {
        return pageSize * (page - 1);
    }
}
