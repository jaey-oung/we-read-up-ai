package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RankedBookDto {

    private CategoryDto bookAndCategory;
    private List<String> writers;

    public RankedBookDto(CategoryDto bookAndCategory, List<String> writers) {
        this.bookAndCategory = bookAndCategory;
        this.writers = writers;
    }
}
