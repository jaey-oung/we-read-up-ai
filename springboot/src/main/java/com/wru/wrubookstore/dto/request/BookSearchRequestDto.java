package com.wru.wrubookstore.dto.request;

import com.wru.wrubookstore.domain.HomeSearchCondition;
import com.wru.wrubookstore.dto.request.category.CategoryRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookSearchRequestDto {
    HomeSearchCondition sc;
    CategoryRequest ctg;

    public BookSearchRequestDto() {
    }

    public BookSearchRequestDto(HomeSearchCondition homeSearchCondition, CategoryRequest categoryResponse) {
        this.sc = homeSearchCondition;
        this.ctg = categoryResponse;
    }
}
