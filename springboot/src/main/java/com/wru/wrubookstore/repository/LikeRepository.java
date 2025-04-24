package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;

import java.util.List;
import java.util.Map;

public interface LikeRepository {
    // 해당 책의 좋아요 수 조회
    Integer likeCount(Integer bookId) throws Exception;
    // 현재 유저가 해당 책을 좋아요 눌렀는지 확인
    Integer selectLikeMember(LikeDto likeDto) throws Exception;
    // 해당 회원의 좋아요 수 조회
    int selectCntByMember(Integer memberId) throws Exception;
    // 회원의 좋아요 목록 페이징에 맞게 출력
    List<BookDto> selectListByPh(Map<String, Object> map) throws Exception;
    // 해당 책을 좋아요에 추가
    int insertLike(LikeDto likeDto) throws Exception;
    // 해당 책에 누른 좋아요 삭제
    int deleteLike(LikeDto likeDto) throws Exception;
    // 해당 회원의 좋아요 모두 삭제
    void deleteAll(Integer memberId) throws Exception;
    // 마이페이지에서 선택한 항목 좋아요 삭제
    void deleteSelected(Map<String, Object> map) throws Exception;
}
