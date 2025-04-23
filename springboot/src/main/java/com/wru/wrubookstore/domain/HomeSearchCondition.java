package com.wru.wrubookstore.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainSearchCondition {
    private Integer page = 1;
    private Integer pageSize = 8;
    private String keyword = "";
    private String option = "";
    private Integer sort = 0;

    public MainSearchCondition() {}

    public MainSearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }

    public MainSearchCondition(Integer page, Integer pageSize) { // 테스트 케이스 수정 필요
        this.page = page;
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (page - 1) * pageSize;
    }

    public Integer getLimit() {
        return pageSize;
    }
}
