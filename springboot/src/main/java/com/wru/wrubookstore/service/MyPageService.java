package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.request.MyPageRequest;

public interface MyPageService {

    // 회원, 비회원에 따른 마이페이지 정보 출력
    MyPageRequest selectMyPageInfo(Integer userId, boolean isMember) throws Exception;
}