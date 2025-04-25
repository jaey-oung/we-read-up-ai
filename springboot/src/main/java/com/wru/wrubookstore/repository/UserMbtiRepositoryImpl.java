package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.UserMbtiDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserMbtiRepositoryImpl implements UserMbtiRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.UserMbtiMapper.";
    private final SqlSessionTemplate session;

    public UserMbtiRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    // 사용자 성향 점수 등록
    @Override
    public int insert(UserMbtiDto userMbtiDto) {
        return session.insert(namespace+"insert", userMbtiDto);
    }

    // 특정 사용자 성향 점수 조회
    @Override
    public UserMbtiDto selectByUserId(int userId) {
        return session.selectOne(namespace+"selectByUserId", userId);
    }

    // 사용자 성향 점수 갱신
    @Override
    public int update(UserMbtiDto userMbtiDto) {
        return session.update(namespace+"update", userMbtiDto);
    }
}
