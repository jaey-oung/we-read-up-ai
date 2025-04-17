//package com.wru.wrubookstore.domain;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PageHandlerTest {
//
//    @Test
//    public void phTest1() {
//        PageHandler ph = new PageHandler(101, 11);
//
//        assertEquals(ph.getTotalCnt(), 101);
//        assertEquals(ph.getTotalPage(),  11);
//        assertTrue(ph.isShowPrev());
//        assertFalse(ph.isShowNext());
//        assertEquals(ph.getBeginPage(), 11);
//        assertEquals(ph.getEndPage(), 11);
//    }
//}