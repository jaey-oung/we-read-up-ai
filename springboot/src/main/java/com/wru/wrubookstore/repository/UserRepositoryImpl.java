package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.UserDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.UserMapper.";
    private final SqlSessionTemplate session;

    public UserRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public UserDto selectUser(Integer userId) throws Exception{
        return session.selectOne(namespace + "selectUser", userId);
    }
    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    @Override
    public List<UserDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public void deleteAll() throws Exception {
        session.delete(namespace+"deleteAll");
    }

    @Override
    public int insert(UserDto userDto) throws Exception {
        return session.insert(namespace+"insert", userDto);
    }

    @Override
    public UserDto select(Integer userId) throws Exception {
        return session.selectOne(namespace+"select", userId);
    }

    @Override
    public UserDto selectByEmailAndPassword(String email, String password) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return session.selectOne(namespace+"selectByEmailAndPassword", map);
    }

    @Override
    public int update(UserDto userDto) throws Exception {
        return session.update(namespace+"update", userDto);
    }

    @Override
    public int delete(String email) throws Exception {
        return session.delete(namespace+"delete", email);
    }

    @Override
    public int isEmailDuplicated(String email) throws Exception {
        return session.selectOne(namespace+"isEmailDuplicated", email);
    }

}
