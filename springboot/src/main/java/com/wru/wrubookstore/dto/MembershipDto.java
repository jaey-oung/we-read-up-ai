package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MembershipDto {

    private String membershipId;
    private String name;
    private double mileagePercent;
    private int membershipCriteria;
}
