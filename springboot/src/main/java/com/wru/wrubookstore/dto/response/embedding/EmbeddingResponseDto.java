package com.wru.wrubookstore.dto.response.embedding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

// 사용자 성향 점수 응답 DTO
// FastAPI → Spring 간 연동 시 JSON key 유지 (@JsonProperty 사용)
@Getter
@Setter
@ToString
public class EmbeddingResponseDto {
    @JsonProperty("S")
    private BigDecimal mbtiS;

    @JsonProperty("I")
    private BigDecimal mbtiI;

    @JsonProperty("F")
    private BigDecimal mbtiF;

    @JsonProperty("D")
    private BigDecimal mbtiD;

    @JsonProperty("N")
    private BigDecimal mbtiN;

    @JsonProperty("M")
    private BigDecimal mbtiM;

    @JsonProperty("Q")
    private BigDecimal mbtiQ;

    @JsonProperty("A")
    private BigDecimal mbtiA;

    public EmbeddingResponseDto() {}

    public EmbeddingResponseDto(BigDecimal mbtiS, BigDecimal mbtiI, BigDecimal mbtiF, BigDecimal mbtiD,
                               BigDecimal mbtiN, BigDecimal mbtiM, BigDecimal mbtiQ, BigDecimal mbtiA) {
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
