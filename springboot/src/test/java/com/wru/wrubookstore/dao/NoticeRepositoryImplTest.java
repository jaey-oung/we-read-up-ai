//package com.wru.wrubookstore.dao;
//
//import com.wru.wrubookstore.dto.NoticeDto;
//import com.wru.wrubookstore.repository.NoticeRepositoryImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class NoticeRepositoryImplTest {
//
//    @Autowired
//    private NoticeRepositoryImpl noticeRepositoryImpl;
//
//    @Test
//    public void select() throws Exception{
//        assertTrue(noticeRepositoryImpl !=null);
//        System.out.println("noticeDao = " + noticeRepositoryImpl);
//        NoticeDto noticeDto = noticeRepositoryImpl.select(1);
//        System.out.println("noticeDto = " + noticeDto);
////        assertTrue(noticeDto.getNotice_id().equals(1));
//    }
//}