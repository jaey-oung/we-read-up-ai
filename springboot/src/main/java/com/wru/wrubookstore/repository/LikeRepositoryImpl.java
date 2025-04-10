package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LikeRepositoryImpl implements LikeRepository {
    SqlSessionTemplate session;

    LikeRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    String namespace = "com.wru.wrubookstore.mapper.LikeMapper.";

    // 해당 책의 좋아요 수
    @Override
    public Integer likeCount(Integer bookId) throws Exception{
        return session.selectOne(namespace + "likeCount", bookId);
    }

    // 현재 유저가 해당 책을 좋아요 눌렀는지 확인
    @Override
    public Integer selectLikeMember(LikeDto likeDto) throws Exception{
        return session.selectOne(namespace + "selectLikeMember", likeDto);
    }

    @Override
    public int selectCntByMember(Integer memberId) throws Exception {
        return session.selectOne(namespace + "selectCntByMember", memberId);
    }

    @Override
    public List<BookDto> selectListByPh(Map<String, Object> map) throws Exception {
        return session.selectList(namespace + "selectListByPh", map);
    }

    // 해당 책을 좋아요에 추가
    @Override
    public void insertLike(LikeDto likeDto) throws Exception{
        session.insert(namespace + "insertLike", likeDto);
    }
    // 해당 책에 누른 좋아요 삭제
    @Override
    public void deleteLike(LikeDto likeDto) throws Exception{
        session.delete(namespace + "deleteLike", likeDto);
    }

    @Override
    public void deleteAll(Integer memberId) throws Exception {
        session.delete(namespace + "deleteAll", memberId);
    }

    @Override
    public void deleteSelected(Map<String, Object> map) throws Exception {
        session.delete(namespace + "deleteSelected", map);
    }
}
