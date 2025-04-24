package com.wru.wrubookstore.dto;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookFilterDto {
    HomeSearchCondition sc;
    CategoryRequest ctg;

    public BookFilterDto() {
    }

    public BookFilterDto(HomeSearchCondition homeSearchCondition, CategoryRequest categoryResponse) {
        this.sc = homeSearchCondition;
        this.ctg = categoryResponse;
    }
}
