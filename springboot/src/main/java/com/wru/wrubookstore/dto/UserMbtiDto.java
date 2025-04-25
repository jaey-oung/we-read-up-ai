package com.wru.wrubookstore.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

// 사용자 MBTI 성향 정보를 저장하는 DTO
// user_mbti 테이블과 매핑
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"userMbtiId", "userId"})
public class UserMbtiDto {
    private Integer userMbtiId;
    private Integer userId;

    private BigDecimal mbtiS;
    private BigDecimal mbtiI;
    private BigDecimal mbtiF;
    private BigDecimal mbtiD;
    private BigDecimal mbtiN;
    private BigDecimal mbtiM;
    private BigDecimal mbtiQ;
    private BigDecimal mbtiA;

    private LocalDate regDate;
    private LocalDate modDate;

    public UserMbtiDto() {}

    public UserMbtiDto(Integer userId, BigDecimal mbtiS, BigDecimal mbtiI, BigDecimal mbtiF, BigDecimal mbtiD,
                       BigDecimal mbtiN, BigDecimal mbtiM, BigDecimal mbtiQ, BigDecimal mbtiA) {
        this.userId = userId;
        this.mbtiS = mbtiS;
        this.mbtiI = mbtiI;
        this.mbtiF = mbtiF;
        this.mbtiD = mbtiD;
        this.mbtiN = mbtiN;
        this.mbtiM = mbtiM;
        this.mbtiQ = mbtiQ;
        this.mbtiA = mbtiA;
    }
}
