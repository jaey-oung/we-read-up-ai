package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.InquiryDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository {

    private final SqlSessionTemplate session;
    String namespace = "com.wru.wrubookstore.mapper.InquiryMapper.";

    public InquiryRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public InquiryDto select(Integer inquiryId) throws Exception{
        return session.selectOne(namespace + "select", inquiryId);
    }

    @Override
    public List<InquiryDto> selectAll(int memberId) throws Exception{
        return session.selectList(namespace + "selectAll", memberId);
    }

    @Override
    public List<InquiryDto> selectForEmp() throws Exception{
        return session.selectList(namespace + "selectForEmp");
    }

    @Override
    public int count() throws Exception{
        return session.selectOne(namespace+"count");
    }

    @Override
    public int insert(InquiryDto inquiryDto) throws Exception{
        return session.insert(namespace + "insert", inquiryDto);
    }

    @Override
    public int delete(Integer inquiryId, Integer memberId) throws Exception{
        Map map = new HashMap();
        map.put("inquiryId", inquiryId);
        map.put("memberId", memberId);
        return session.delete(namespace+"delete", map);
    }

    @Override
    public int deleteForAdmin(Integer inquiryId) throws Exception{
        Map map = new HashMap();
        map.put("inquiryId", inquiryId);
        return session.delete(namespace+"deleteForAdmin", map);
    }

    @Override
    public int update(InquiryDto inquiryDto) throws Exception{
        return session.update(namespace+"update", inquiryDto);
    }

    @Override
    public int updateInquiryStatus(Map<String, Object> map) throws Exception {
        return session.update(namespace + "updateInquiryStatus", map);
    }

    @Override
    public int updateReply(InquiryDto inquiryDto) throws Exception{
        return session.update(namespace+"updateReply", inquiryDto);
    }


}
