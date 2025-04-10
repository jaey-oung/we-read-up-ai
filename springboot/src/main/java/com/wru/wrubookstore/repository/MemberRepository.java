package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface MemberRepository {

    int count() throws Exception;
    List<MemberDto> selectAll() throws Exception;
    void deleteAllMembers() throws Exception;
    void deleteAllUsers() throws Exception;
    int insertUser(UserDto userDto) throws Exception;
    int insertUserWithId(MemberDto memberDto) throws Exception;
    int insertMember(MemberDto memberDto) throws Exception;
    MemberDto select(Integer userId) throws Exception;
    MemberDto selectMember(Integer userId) throws Exception;
    MemberDto selectByNameAndPhoneNum(MemberDto memberDto) throws Exception;
    MemberDto selectByEmailAndNameAndPhoneNum(MemberDto memberDto) throws Exception;
    int updateUser(MemberDto memberDto) throws Exception;
    int updateMember(MemberDto memberDto) throws Exception;
    // 사용 마일리치 차감 및 구매로 인한 마일리지 추가
    int updateMileage(Map<String, Integer> map) throws Exception;
    // 구매 시 전월구매금액 추가
    int updateLastMonthAmount(Map<String, Integer> map) throws Exception;
    int deleteMember(Integer userId) throws Exception;
    int deleteUser(Integer userId) throws Exception;
    int countMembers() throws Exception;

}
