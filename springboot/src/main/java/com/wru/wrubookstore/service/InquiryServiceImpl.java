package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.InquiryDto;
import com.wru.wrubookstore.repository.InquiryRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepositoryImpl inquiryRepository;

    public InquiryServiceImpl(InquiryRepositoryImpl inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public List<InquiryDto> getList(int memberId) throws Exception {

        System.out.println("Service_memberId = " + memberId);
        List<InquiryDto> list = inquiryRepository.selectAll(memberId);
        System.out.println("Service_list = " + list);

        return list;
    }

    @Override
    public List<InquiryDto> getAllList()throws Exception{
        return inquiryRepository.selectForEmp();
    }

    @Override
    public int write(InquiryDto inquiryDto) throws Exception{
        inquiryDto.setInquiryStatusId("ID_1");    // 고객 문의 등록시 상태를 "ID_1"으로 설정
        inquiryRepository.insert(inquiryDto);

        Map<String, Object> params = new HashMap<>();
        params.put("inquiryId", inquiryDto.getInquiryId());
        params.put("inquiryStatusId", "ID_1");
        inquiryRepository.updateInquiryStatus(params);

        return inquiryDto.getInquiryId();
    }

    @Override
    public int remove(Integer inquiryId, Integer memberId) throws Exception{
        return inquiryRepository.delete(inquiryId, memberId);
    }

    @Override
    public int removeForAdmin(Integer inquiryId) throws Exception{
        return inquiryRepository.deleteForAdmin(inquiryId);
    }

    @Override
    public int modify(InquiryDto inquiryDto) throws Exception{
        return inquiryRepository.update(inquiryDto);
    }

    @Override
    public int reply(InquiryDto inquiryDto) throws Exception {
        inquiryRepository.updateReply(inquiryDto);

        Map<String, Object> params = new HashMap<>();
        params.put("inquiryId", inquiryDto.getInquiryId());
        params.put("inquiryStatusId", "inq_2");
        inquiryRepository.updateInquiryStatus(params);

        return inquiryDto.getInquiryId();
    }
}
