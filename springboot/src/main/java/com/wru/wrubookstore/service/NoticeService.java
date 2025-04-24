package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.SearchCondition;
import com.wru.wrubookstore.dto.NoticeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    int getCount() throws Exception;

    @Transactional
    int remove(Integer noticeId, String employeeId) throws Exception;

    int write(NoticeDto noticeDto) throws Exception;

    List<NoticeDto> getList() throws Exception;

    NoticeDto read(Integer noticeId) throws Exception;

    List<NoticeDto> getPage(Map map) throws Exception;

    int modify(NoticeDto noticeDto) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;

    List<NoticeDto> getSearchResultPage(SearchCondition sc) throws Exception;
}
