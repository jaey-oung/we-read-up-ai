package com.wru.wrubookstore.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.ExchangeMapper.";
    private final SqlSessionTemplate session;

    public ExchangeRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public int selectCnt(Integer userId) throws Exception {
        return session.selectOne(namespace + "selectCnt", userId);
    }
}
