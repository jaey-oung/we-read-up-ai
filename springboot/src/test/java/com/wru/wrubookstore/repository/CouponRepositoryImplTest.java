//package com.wru.wrubookstore.repository;
//
//import com.wru.wrubookstore.dto.CouponDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CouponRepositoryImplTest {
//
//    @Autowired
//    CouponRepository couponRepository;
//
//    Integer userId = 3;
//
//    @Test
//    public void selectListTest() throws Exception {
//        List<CouponDto> couponDtoList = couponRepository.selectList(userId);
//
//        assertEquals(couponDtoList.size(), 3);
//    }
//
//    @Test
//    public void selectCountTest() throws Exception {
//        int couponCount = couponRepository.selectCount(userId);
//
//        assertEquals(couponCount, 3);
//    }
//}