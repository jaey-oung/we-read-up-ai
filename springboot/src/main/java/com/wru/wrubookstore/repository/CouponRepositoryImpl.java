package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.CouponDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.CouponMapper.";
    private final SqlSessionTemplate session;

    public CouponRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public List<CouponDto> selectList(Integer userId) throws Exception {
        return session.selectList(namespace + "selectList", userId);
    }

    @Override
    public int selectCount(Integer userId) throws Exception {
        return session.selectOne(namespace + "selectCount", userId);
    }
}
