package com.wru.wrubookstore.domain;

import lombok.Getter;

@Getter
public enum SortOption { // 정렬 기준 enum (기존 매직 넘버 대체 예정)

    LATEST(0, "최신순"),
    PRICE_ASC(1, "낮은 가격 순"),
    PRICE_DESC(2, "높은 가격 순");

    private final int value;
    private final String label;

    SortOption(int value, String label) {
        this.value = value;
        this.label = label;
    }
}
