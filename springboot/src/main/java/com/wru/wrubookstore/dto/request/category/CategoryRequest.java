package com.wru.wrubookstore.dto.request.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryRequest {
    private String categoryLargeId;
    private String categoryMediumId;
    private String categorySmallId;

    public CategoryRequest() {}

    public CategoryRequest(String categoryLargeId, String categoryMediumId, String categorySmallId) {
        this.categoryLargeId = categoryLargeId;
        this.categoryMediumId = categoryMediumId;
        this.categorySmallId = categorySmallId;
    }
}
