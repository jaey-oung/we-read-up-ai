package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.FaqDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FaqRepositoryImpl implements FaqRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.FaqMapper.";
    private final SqlSessionTemplate session;

    public FaqRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public FaqDto select(Integer faqId) throws Exception{
        return session.selectOne(namespace + "select", faqId);
    }

    @Override
    public List<FaqDto> selectAll() throws Exception{
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public Integer count() throws Exception{
        return session.selectOne(namespace + "count");
    }

    @Override
    public Integer deleteAll() throws Exception{
        return session.selectOne(namespace + "deleteAll");
    }

    @Override
    public Integer delete(Integer faqId, String employeeId) throws Exception{
        Map map = new HashMap();
        map.put("faqId", faqId);
        map.put("employeeId", employeeId);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public Integer insert(FaqDto dto) throws Exception{
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public Integer update(FaqDto dto) throws Exception{
        return session.update(namespace + "update", dto);
    }

}
