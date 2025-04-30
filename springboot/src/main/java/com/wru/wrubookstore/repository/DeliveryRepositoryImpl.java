package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.DeliveryDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.DeliveryMapper.";
    private final SqlSessionTemplate session;

    public DeliveryRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public DeliveryDto select(Integer orderId) throws Exception {
        return session.selectOne(namespace + "select", orderId);
    }

    @Override
    public int insert(DeliveryDto deliveryDto) throws Exception {
        return session.insert(namespace + "insert", deliveryDto);
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }
}
