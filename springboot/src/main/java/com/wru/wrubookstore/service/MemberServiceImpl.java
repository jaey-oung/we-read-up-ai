package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int count() throws Exception {
        return memberRepository.count();
    }

    @Override
    public List<MemberDto> selectAll() throws Exception {
        return memberRepository.selectAll();
    }

    @Override
    public void deleteAllMembers() throws Exception {
        memberRepository.deleteAllMembers();
    }

    @Override
    public void deleteAllUsers() throws Exception {
        memberRepository.deleteAllUsers();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(UserDto userDto, MemberDto memberDto) throws Exception {
        int rowCnt1 = memberRepository.insertUser(userDto);

        if (rowCnt1 != 1)
                throw new Exception("사용자 테이블에 회원 계정 생성 실패");

        int rowCnt2 = memberRepository.insertMember(memberDto);

        if (rowCnt2 != 1)
            throw new Exception("회원 테이블에 회원 계정 생성 실패");
    }

    @Override
    public MemberDto select(Integer userId) throws Exception {
        return memberRepository.select(userId);
    }

    @Override
    public MemberDto selectMember(Integer userId) throws Exception{
        return memberRepository.selectMember(userId);
    }

    @Override
    public MemberDto selectByNameAndPhoneNum(MemberDto memberDto) throws Exception {
        return memberRepository.selectByNameAndPhoneNum(memberDto);
    }

    @Override
    public MemberDto selectByEmailAndNameAndPhoneNum(MemberDto memberDto) throws Exception {
        return memberRepository.selectByEmailAndNameAndPhoneNum(memberDto);
    }

    @Override
    public int updateUser(MemberDto memberDto) throws Exception {
        return memberRepository.updateUser(memberDto);
    }

    @Override
    public int updateMember(MemberDto memberDto) throws Exception {
        return memberRepository.updateMember(memberDto);
    }

    @Override
    public int deleteMember(Integer userId) throws Exception {
        return memberRepository.deleteMember(userId);
    }

    @Override
    public int deleteUser(Integer userId) throws Exception {
        return memberRepository.deleteUser(userId);
    }

    @Override
    public int countMembers() throws Exception {
        return memberRepository.countMembers();
    }

    @Override
    @Transactional
    public void editMember(MemberDto memberDto) throws Exception {
        memberRepository.updateUser(memberDto);
        memberRepository.updateMember(memberDto);
    }

    @Override
    @Transactional
    public void withdraw(Integer userId) throws Exception {
        memberRepository.deleteUser(userId);
        memberRepository.deleteMember(userId);
    }

    @Override
    @Transactional
    public void convertToMember(MemberDto memberDto) throws Exception {
        // 기존 사용자 삭제
        memberRepository.deleteUser(memberDto.getUserId());
        // 새로운 사용자 생성
        memberRepository.insertUserWithId(memberDto);
        // 새로운 회원 생성
        memberRepository.insertMember(memberDto);
    }

}
