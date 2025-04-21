package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;

import java.util.List;
import java.util.Map;

public interface LikeService {
    // 해당 책의 좋아요 수 조회
    Integer likeCount(Integer bookId) throws Exception;

    // 해당 책을 세션 유저가 좋아요 했는지 조회
    Integer selectLikeMember(Integer bookId, Integer userId) throws Exception;

    // 해당 회원의 좋아요 수 조회
    int selectCntByMember(Integer memberId) throws Exception;

    // 회원의 좋아요 목록 페이징에 맞게 출력
    List<BookDto> selectListByPh(Integer memberId, PageHandler ph) throws Exception;

    // 해당 책을 좋아요에 추가
    String insertLike(LikeDto likeDto, Integer userId) throws Exception;

    // 해당 책에 누른 좋아요 삭제
    String deleteLike(LikeDto likeDto, Integer userId) throws Exception;

    // 해당 회원의 좋아요 모두 삭제
    void deleteAll(Integer memberId) throws Exception;

    // 마이페이지에서 선택한 항목 좋아요 삭제
    void deleteSelected(Integer memberId, List<Integer> bookIdList) throws Exception;
}
