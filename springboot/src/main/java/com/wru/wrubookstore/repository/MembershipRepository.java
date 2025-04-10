package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.MembershipDto;

public interface MembershipRepository {

    /* select */
    // 회원의 멤버십 정보 조회
    MembershipDto select(Integer userId) throws Exception;
}
