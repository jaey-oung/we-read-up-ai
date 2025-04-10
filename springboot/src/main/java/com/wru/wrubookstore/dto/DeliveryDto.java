package com.wru.wrubookstore.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class DeliveryDto {

    private Integer deliveryId;
    private Integer orderId;
    private String statusId;

    @NotBlank(message = "수령인 이름을 입력해주세요.")
    private String recipient;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNum;

    @NotNull(message = "우편번호를 입력해주세요.")
    @Min(value = 10000, message = "올바른 우편번호 형식이 아닙니다.")
    @Max(value = 99999, message = "올바른 우편번호 형식이 아닙니다.")
    private int zipCode;

    @NotBlank(message = "기본주소를 입력해주세요.")
    private String mainAddress;
    private String detailAddress;
    private String message;
    private Date regDate;
    private Date endDate;
    private String waybill;

    public DeliveryDto() {

    }

    public DeliveryDto(Integer orderId, String recipient, String phoneNum, int zipCode, String mainAddress, String detailAddress, String message) {
        this.orderId = orderId;
        this.recipient = recipient;
        this.phoneNum = phoneNum;
        this.zipCode = zipCode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.message = message;
    }
}
