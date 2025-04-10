package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.CouponDto;
import com.wru.wrubookstore.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<CouponDto> selectList(Integer userId) throws Exception {
        return couponRepository.selectList(userId);
    }

    @Override
    public int selectCount(Integer userId) throws Exception {
        return couponRepository.selectCount(userId);
    }
}
