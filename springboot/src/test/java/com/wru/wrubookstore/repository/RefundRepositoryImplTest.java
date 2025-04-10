package com.wru.wrubookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefundRepositoryImplTest {

    @Autowired
    RefundRepository refundRepository;

    Integer userId = 3;

    @Test
    public void selectCntTest() throws Exception {
        int refundCnt = refundRepository.selectCnt(userId);

        assertEquals(refundCnt, 1);
    }

}