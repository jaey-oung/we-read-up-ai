package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.EmployeeDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.EmployeeMapper.";
    private final SqlSessionTemplate session;

    public EmployeeRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public EmployeeDto selectByEmailAndPassword(String email, String password) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return session.selectOne(namespace+"selectByEmailAndPassword", map);
    }

    @Override
    public int isEmailDuplicated(String email) throws Exception {
        return session.selectOne(namespace+"isEmailDuplicated", email);
    }
}
