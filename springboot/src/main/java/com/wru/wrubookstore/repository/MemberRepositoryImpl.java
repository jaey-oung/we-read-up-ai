package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.MemberMapper.";
    private final SqlSessionTemplate session;

    public MemberRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    @Override
    public List<MemberDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public void deleteAllMembers() throws Exception {
        session.delete(namespace+"deleteAllMembers");
    }

    @Override
    public void deleteAllUsers() throws Exception {
        session.delete(namespace+"deleteAllUsers");
    }

    @Override
    public int insertUser(UserDto userDto) throws Exception {
        return session.insert(namespace+"insertUser", userDto);
    }

    @Override
    public int insertUserWithId(MemberDto memberDto) throws Exception {
        return session.insert(namespace + "insertUserWithId", memberDto);
    }

    @Override
    public int insertMember(MemberDto memberDto) throws Exception {
        return session.insert(namespace+"insertMember", memberDto);
    }

    @Override
    public MemberDto select(Integer userId) throws Exception {
        return session.selectOne(namespace+"select", userId);
    }

    @Override
    public MemberDto selectMember(Integer userId) throws Exception {
        return session.selectOne(namespace+"selectMember", userId);
    }

    @Override
    public MemberDto selectByNameAndPhoneNum(MemberDto memberDto) throws Exception {
        return session.selectOne(namespace + "selectByNameAndPhoneNum", memberDto);
    }

    @Override
    public MemberDto selectByEmailAndNameAndPhoneNum(MemberDto memberDto) throws Exception {
        return session.selectOne(namespace + "selectByEmailAndNameAndPhoneNum", memberDto);
    }

    @Override
    public int updateUser(MemberDto memberDto) throws Exception {
        return session.update(namespace+"updateUser", memberDto);
    }

    @Override
    public int updateMember(MemberDto memberDto) throws Exception {
        return session.update(namespace+"updateMember", memberDto);
    }

    @Override
    public int updateMileage(Map<String, Integer> map) throws Exception {
        return session.update(namespace + "updateMileage", map);
    }

    @Override
    public int updateLastMonthAmount(Map<String, Integer> map) throws Exception {
        return session.update(namespace + "updateLastMonthAmount", map);
    }

    @Override
    public int deleteMember(Integer userId) throws Exception {
        return session.delete(namespace+"deleteMember", userId);
    }

    @Override
    public int deleteUser(Integer userId) throws Exception {
        return session.delete(namespace+"deleteUser", userId);
    }

    @Override
    public int countMembers() throws Exception {
        return session.selectOne(namespace+"countMembers");
    }

}
