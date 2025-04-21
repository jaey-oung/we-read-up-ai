package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.repository.LikeRepository;
import com.wru.wrubookstore.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;

    LikeServiceImpl(LikeRepository likeRepository, MemberService memberService){
        this.likeRepository = likeRepository;
        this.memberService = memberService;
    }

    // 해당 책의 좋아요 수 조회
    @Override
    public Integer likeCount(Integer bookId) throws Exception{
        return likeRepository.likeCount(bookId);
    }

    // 현재 유저가 해당 책을 좋아요 눌렀는지 확인
    @Override
    public Integer selectLikeMember(Integer bookId, Integer userId) throws Exception{
        // 해당 책에 대하여 현재 유저가 좋아요를 안눌렀을 경우
        final int LIKE_OFF = 0;

        try{
            // 비로그인시 좋아요를 누르지 않은 상태를 반환
            if(userId == null) return LIKE_OFF;

            // 맴버 아이디 조회
            MemberDto memberDto = memberService.selectMember(userId);

            int memberId = memberDto.getMemberId();

            LikeDto likeDto = new LikeDto(bookId, memberId);

            // 로그인 상태일 경우 해당 상품에 대한 회원의 좋아요 상태를 반환
            return likeRepository.selectLikeMember(likeDto);
        } catch(Exception e){
            e.printStackTrace();
            return LIKE_OFF;
        }
    }

    @Override
    public int selectCntByMember(Integer memberId) throws Exception {
        return likeRepository.selectCntByMember(memberId);
    }

    @Override
    public List<BookDto> selectListByPh(Integer memberId, PageHandler ph) throws Exception {
        return likeRepository.selectListByPh(Map.of("memberId", memberId, "ph", ph));
    }

    // 해당 책을 좋아요에 추가
    @Override
    public void insertLike(LikeDto likeDto) throws Exception{
        likeRepository.insertLike(likeDto);
    }
    // 해당 책에 누른 좋아요 삭제
    @Override
    public void deleteLike(LikeDto likeDto) throws Exception{
        likeRepository.deleteLike(likeDto);
    }

    @Override
    public void deleteAll(Integer memberId) throws Exception {
        likeRepository.deleteAll(memberId);
    }

    @Override
    public void deleteSelected(Integer memberId, List<Integer> bookIdList) throws Exception {
        likeRepository.deleteSelected(Map.of("memberId", memberId, "bookIdList", bookIdList));
    }
}
