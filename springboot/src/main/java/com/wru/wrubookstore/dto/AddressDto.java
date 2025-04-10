package com.wru.wrubookstore.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class AddressDto {

    private Integer addressId;
    private Integer memberId;

    @NotBlank(message = "배송지명을 입력해주세요.")
    private String name;

    @NotBlank(message = "수령인 이름을 입력해주세요.")
    private String recipient;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNum;

    @NotNull(message = "우편번호를 입력해주세요")
    @Min(value = 10000, message = "올바른 우편번호 형식이 아닙니다.")
    @Max(value = 99999, message = "올바른 우편번호 형식이 아닙니다.")
    private Integer zipCode;

    @NotBlank(message = "기본주소를 입력해주세요.")
    private String mainAddress;
    private String detailAddress;
    private boolean defaultAddress;

    public AddressDto() {
    }

    public AddressDto(Integer memberId, String name, String recipient, String phoneNum, int zipCode, String mainAddress, String detailAddress, boolean defaultAddress) {
        this.memberId = memberId;
        this.name = name;
        this.recipient = recipient;
        this.phoneNum = phoneNum;
        this.zipCode = zipCode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.defaultAddress = defaultAddress;
    }
}
