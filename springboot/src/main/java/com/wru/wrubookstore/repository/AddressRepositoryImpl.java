package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.AddressDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.AddressMapper.";
    private final SqlSessionTemplate session;

    public AddressRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public List<AddressDto> selectList(Integer memberId) throws Exception {
        return session.selectList(namespace + "selectList", memberId);
    }

    @Override
    public AddressDto selectById(Integer addressId) throws Exception {
        return session.selectOne(namespace + "selectById", addressId);
    }

    @Override
    public AddressDto selectDefaultAddress(Integer userId) throws Exception {
        return session.selectOne(namespace + "selectDefaultAddress", userId);
    }

    @Override
    public int insert(AddressDto addressDto) throws Exception {
        return session.insert(namespace + "insert", addressDto);
    }

    @Override
    public int update(AddressDto addressDto) throws Exception {
        return session.update(namespace + "update", addressDto);
    }

    @Override
    public int unsetDefaultAddress(Integer memberId) throws Exception {
        return session.update(namespace + "unsetDefaultAddress", memberId);
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }
}
