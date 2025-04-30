package com.wru.wrubookstore.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CouponRepositoryImplTest {

    @Autowired
    private CouponRepository couponRepository;

    Integer memberId = 1;
    Integer userId = 3;

    @BeforeEach
    @DisplayName("해당 멤버 쿠폰 삭제")
    public void beforeEach() throws Exception {
        int deleteCnt = couponRepository.deleteAll(memberId);
    }

    @Test
    @DisplayName("해당 회원의 쿠폰 개수 테스트")
    public void selectCountTest() throws Exception {
        /* given */

        /* when */
        int couponCnt = couponRepository.selectCount(userId);

        /* then */
        assertEquals(0, couponCnt);
    }
}