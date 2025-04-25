package com.wru.wrubookstore.dto.request.mbti;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

// 사용자 성향 점수 요청 DTO
@Getter
@Setter
public class MbtiRequestDto {
    private Map<String, Float> scores;
}
