package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.CouponDto;

import java.util.List;

public interface CouponService {
    List<CouponDto> selectList(Integer userId) throws Exception;
    int selectCount(Integer userId) throws Exception;
}
