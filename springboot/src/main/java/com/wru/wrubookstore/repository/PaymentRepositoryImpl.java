package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.PaymentDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.PaymentMapper.";
    private final SqlSessionTemplate session;

    public PaymentRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public PaymentDto select(Integer orderId) throws Exception {
        return session.selectOne(namespace + "select", orderId);
    }

    @Override
    public int insert(PaymentDto paymentDto) throws Exception {
        return session.insert(namespace + "insert", paymentDto);
    }
}
