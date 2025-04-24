package com.wru.wrubookstore.service;

import com.sun.net.httpserver.Authenticator;
import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.error.exception.*;
import com.wru.wrubookstore.repository.LikeRepository;
import com.wru.wrubookstore.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;

    // 해당 책에 대하여 현재 유저가 좋아요를 안눌렀을 경우
    final int LIKE_OFF = 0;
    // 해당 책에 대하여 현재 유저가 좋아요를 눌렀을 경우
    final int LIKE_ON = 1;
    // 해당 쿼리가 실패했을경우
    final int FAIL = 0;

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
    public String insertLike(LikeDto likeDto, Integer userId) throws Exception{
        MemberDto memberDto = memberService.selectMember(userId);
        if(memberDto == null) {
            throw new MemberNotFoundException(userId);
        }
        likeDto.setMemberId(memberDto.getMemberId());
        // 이미 좋아요를 누른 책을 좋아요 할 경우
        if(likeRepository.selectLikeMember(likeDto) == LIKE_ON){
            throw new DuplicateLikeException("이미 좋아요를 눌렀습니다. 잠시후 다시 시도해주세요.",likeDto);
        }

        int result = likeRepository.insertLike(likeDto);
        // 좋아요 추가에 실패했을경우
        if(result == FAIL){
            throw new LikeInsertFailedException("좋아요 등록에 실패했습니다.",likeDto);
        }

        return "success";
    }


    @Override
    public String deleteLike(LikeDto likeDto, Integer userId) throws Exception{
        MemberDto memberDto = memberService.selectMember(userId);
        if(memberDto == null) {
            throw new MemberNotFoundException(userId);
        }
        likeDto.setMemberId(memberDto.getMemberId());
        // 삭제할 좋아요가 없을 경우
        if(likeRepository.selectLikeMember(likeDto) == LIKE_OFF){
            throw new LikeNotFoundException("취소할 대상이 없습니다.",likeDto);
        }

        int result = likeRepository.deleteLike(likeDto);
        // 좋아요 삭제에 실패했을 경우
        if(result == FAIL){
            throw new LikeDeleteFailedException("좋아요 삭제에 실패했습니다.",likeDto);
        }

        return "success";
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
