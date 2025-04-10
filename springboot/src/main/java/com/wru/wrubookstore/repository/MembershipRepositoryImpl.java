package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.MembershipDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MembershipRepositoryImpl implements MembershipRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.MembershipMapper.";
    private final SqlSessionTemplate session;

    public MembershipRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public MembershipDto select(Integer userId) throws Exception {
        return session.selectOne(namespace + "select", userId);
    }
}
