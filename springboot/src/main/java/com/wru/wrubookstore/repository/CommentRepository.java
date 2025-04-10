package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.CommentDto;

import java.util.List;

public interface CommentRepository {
    int count(Integer noticeId) throws Exception // T selectOne(String statement)
    ;

    int deleteAll(Integer noticeId) // int delete(String statement)
    ;

    int delete(Integer commentId, Integer memberId) throws Exception // int delete(String statement, Object parameter)
    ;

    int insert(CommentDto dto) throws Exception // int insert(String statement, Object parameter)
    ;

    List<CommentDto> selectAll(Integer noticeId) throws Exception // List<E> selectList(String statement)
    ;

    CommentDto select(Integer commentId) throws Exception // T selectOne(String statement, Object parameter)
    ;

    int update(CommentDto dto) throws Exception // int update(String statement, Object parameter)
    ;
}
