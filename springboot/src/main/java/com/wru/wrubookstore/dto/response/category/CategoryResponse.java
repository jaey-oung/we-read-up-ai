package com.wru.wrubookstore.dto.response.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CategoryResponse {
    private String categoryLargeId;
    private String categoryLargeName;
    private String categoryMediumId;
    private String categoryMediumName;
    private String categorySmallId;
    private String categorySmallName;

    CategoryResponse(){}
}
