//package com.wru.wrubookstore.repository;
//
//import com.wru.wrubookstore.domain.PageHandler;
//import com.wru.wrubookstore.dto.BookDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class LikeRepositoryImplTest {
//
//    @Autowired
//    LikeRepository likeRepository;
//    Integer memberId = 1;
//
//    @Test
//    public void selectCntByMemberTest() throws Exception {
//        int likeCnt = likeRepository.selectCntByMember(memberId);
//
//        assertEquals(likeCnt, 2);
//    }
//
//    @Test
//    public void selectListByPhTest() throws Exception {
//        int page = 1;
//        int likeCnt = likeRepository.selectCntByMember(memberId);
//        PageHandler ph = new PageHandler(likeCnt, page);
//
//        List<BookDto> bookDtoList = likeRepository.selectListByPh(Map.of("memberId", memberId, "ph", ph));
//        BookDto firstBookDto = bookDtoList.getFirst();
//
//        assertEquals(bookDtoList.size(), 2);
//        assertEquals(firstBookDto.getBookId(), 1);
//        assertEquals(firstBookDto.getName(), "인생은 순간이다");
//    }
//
//    @Test
//    @Transactional
//    public void deleteAllTest() throws Exception {
//        likeRepository.deleteAll(memberId);
//        int likeCnt = likeRepository.selectCntByMember(memberId);
//
//        assertEquals(likeCnt, 0);
//    }
//
//    @Test
//    @Transactional
//    public void deleteSelectedTest() throws Exception {
//        Integer memberId = 7;
//        List<Integer> bookIdList = new ArrayList<>();
//        bookIdList.add(4);
//        bookIdList.add(5);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("memberId", memberId);
//        map.put("bookIdList", bookIdList);
//
//        likeRepository.deleteSelected(map);
//        int likeCnt = likeRepository.selectCntByMember(memberId);
//
//        assertEquals(likeCnt, 1);
//    }
//}