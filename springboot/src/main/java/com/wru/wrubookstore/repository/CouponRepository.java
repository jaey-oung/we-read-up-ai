package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.CouponDto;

import java.util.List;

public interface CouponRepository {

    /* select */
    List<CouponDto> selectList(Integer userId) throws Exception;
    int selectCount(Integer userId) throws Exception;

    /* delete*/
    int deleteAll(Integer memberId) throws Exception;
}
