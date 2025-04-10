package com.wru.wrubookstore.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefundRepositoryImpl implements RefundRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.RefundMapper.";
    private final SqlSessionTemplate session;

    public RefundRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public int selectCnt(Integer userId) throws Exception {
        return session.selectOne(namespace + "selectCnt", userId);
    }
}
