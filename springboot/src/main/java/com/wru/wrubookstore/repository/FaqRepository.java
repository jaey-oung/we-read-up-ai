package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.FaqDto;

import java.util.List;

public interface FaqRepository {
    FaqDto select(Integer faqId) throws Exception;

    List<FaqDto> selectAll() throws Exception;

    Integer count() throws Exception;

    Integer deleteAll() throws Exception;

    Integer delete(Integer faqId, String employeeId) throws Exception;

    Integer insert(FaqDto dto) throws Exception;

    Integer update(FaqDto dto) throws Exception;
}
