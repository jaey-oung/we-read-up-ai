package com.wru.wrubookstore.helper;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.error.exception.MemberNotFoundException;
import com.wru.wrubookstore.service.MemberService;
import org.springframework.stereotype.Component;

@Component
public class MemberHelper {

    private final MemberService memberService;

    public MemberHelper(MemberService memberService) {
        this.memberService = memberService;
    }

    // 세션의 멤버 아이디 검증기
    public MemberDto getValidMember(Integer userId) throws Exception{
        MemberDto memberDto = memberService.selectMember(userId);

        if(memberDto == null){
            throw new MemberNotFoundException(userId);
        }

        return memberDto;
    }
}
