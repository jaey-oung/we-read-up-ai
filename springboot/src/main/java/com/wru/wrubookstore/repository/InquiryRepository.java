package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.InquiryDto;

import java.util.List;
import java.util.Map;

public interface InquiryRepository {
    InquiryDto select(Integer inquiryId) throws Exception;

    List<InquiryDto> selectAll(int memberId) throws Exception;

    List<InquiryDto> selectForEmp() throws Exception;

    int count() throws Exception;

    int insert(InquiryDto inquiryDto) throws Exception;

    int delete(Integer inquiryId, Integer memberId) throws Exception;

    int deleteForAdmin(Integer inquiryId) throws Exception;

    int update(InquiryDto inquiryDto) throws Exception;

    int updateInquiryStatus(Map<String, Object> map) throws Exception;

    int updateReply(InquiryDto inquiryDto) throws Exception;
}
