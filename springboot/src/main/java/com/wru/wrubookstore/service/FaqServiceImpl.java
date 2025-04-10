package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.FaqDto;
import com.wru.wrubookstore.repository.FaqRepository;
import com.wru.wrubookstore.repository.FaqRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    public FaqServiceImpl(FaqRepositoryImpl faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    public List<FaqDto> getList() throws Exception{
        return faqRepository.selectAll();
    }

    @Override
    public FaqDto read(Integer faqId) throws Exception{
        FaqDto faqDto = faqRepository.select(faqId);

        return faqDto;
    }

    @Override
    public Integer remove(Integer faqId, String employeeId) throws Exception{
        return faqRepository.delete(faqId, employeeId);
    }

    @Override
    public Integer write(FaqDto faqDto) throws Exception{
        return faqRepository.insert(faqDto);
    }

    @Override
    public Integer modify(FaqDto faqDto) throws Exception{
        return faqRepository.update(faqDto);
    }

}
