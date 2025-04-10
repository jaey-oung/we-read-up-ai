package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.FaqDto;

import java.util.List;

public interface FaqService {
    List<FaqDto> getList() throws Exception;

    FaqDto read(Integer faqId) throws Exception;

    Integer remove(Integer faqId, String employeeId) throws Exception;

    Integer write(FaqDto faqDto) throws Exception;

    Integer modify(FaqDto faqDto) throws Exception;
}
