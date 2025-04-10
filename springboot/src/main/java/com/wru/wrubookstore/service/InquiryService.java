package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.InquiryDto;

import java.util.List;

public interface InquiryService {
    List<InquiryDto> getList(int memberId) throws Exception;

    List<InquiryDto> getAllList()throws Exception;

    int write(InquiryDto inquiryDto) throws Exception;

    int remove(Integer inquiryId, Integer memberId) throws Exception;

    int removeForAdmin(Integer inquiryId) throws Exception;

    int modify(InquiryDto inquiryDto) throws Exception;

    int reply(InquiryDto inquiryDto) throws Exception;
}
