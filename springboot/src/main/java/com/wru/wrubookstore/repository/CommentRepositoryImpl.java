package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.CommentDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    SqlSessionTemplate session;
    String namespace = "com.wru.wrubookstore.mapper.commentMapper.";

    @Override
    public int count(Integer noticeId) throws Exception {
        return session.selectOne(namespace+"count", noticeId);
    } // T selectOne(String statement)

    @Override
    public int deleteAll(Integer noticeId) {
        return session.delete(namespace+"deleteAll", noticeId);
    } // int delete(String statement)

    @Override
    public int delete(Integer commentId, Integer memberId) throws Exception {
        Map map = new HashMap();
        map.put("commentId", commentId);
        map.put("memberId", memberId);
        return session.delete(namespace+"delete", map);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(CommentDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<CommentDto> selectAll(Integer noticeId) throws Exception {
        return session.selectList(namespace+"selectCommentsByNoticeId", noticeId);
    } // List<E> selectList(String statement)

    @Override
    public CommentDto select(Integer commentId) throws Exception {
        return session.selectOne(namespace + "select", commentId);
    } // T selectOne(String statement, Object parameter)

    @Override
    public int update(CommentDto dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)
}
