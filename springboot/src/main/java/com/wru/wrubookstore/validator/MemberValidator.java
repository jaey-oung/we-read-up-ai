package com.wru.wrubookstore.validator;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.error.exception.MemberNotFoundException;
import com.wru.wrubookstore.service.MemberService;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {

    private final MemberService memberService;

    public MemberValidator(MemberService memberService) {
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
