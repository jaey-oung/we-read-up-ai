package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.domain.SearchCondition;
import com.wru.wrubookstore.dto.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeRepository {
    NoticeDto select(Integer noticeId) throws Exception // T selectOne(String statement, Object parameter)
    ;

    List<NoticeDto> selectAll() throws Exception // List<E> selectList(String statement)
    ;

    int insert(NoticeDto dto) throws Exception // int insert(String statement, Object parameter)
    ;

    int delete(Integer noticeId, String employeeId) throws Exception // int delete(String statement, Object parameter)
    ;

    int deleteAll() throws Exception // int delete(String statement)
    ;

    int count() throws Exception // T selectOne(String statement)
    ;

    List<NoticeDto> selectPage(Map map) throws Exception // List<E> selectList(String statement, Object parameter)
    ;

    int update(NoticeDto dto) throws Exception // int update(String statement, Object parameter)
    ;

    int increaseViewCnt(Integer noticeId) throws Exception;

    int searchResultCnt(SearchCondition sc) throws Exception // T selectOne(String statement, Object parameter)
    ;

    List<NoticeDto> searchSelectPage(SearchCondition sc) throws Exception // List<E> selectList(String statement, Object parameter)
    ;

    int deleteCommentsByNoticeId(Integer noticeId) throws Exception;
}
