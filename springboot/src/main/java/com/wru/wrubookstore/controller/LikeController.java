package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.service.LikeService;
import com.wru.wrubookstore.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LikeController {
    private final LikeService likeService;
    private final MemberService memberService;

    public LikeController(LikeService likeService, MemberService memberService) {
        this.likeService = likeService;
        this.memberService = memberService;
    }

    @GetMapping("/myPage/like/list")
    public String likeList(@SessionAttribute Integer userId, @RequestParam(defaultValue = "1") int page,
                           Model model) {
        try {
            // userId를 통해 memberId 조회
            Integer memberId = memberService.selectMember(userId).getMemberId();
            // 회원의 좋아요 개수 조회
            int likeCnt = likeService.selectCntByMember(memberId);
            // PageHandler 생성
            PageHandler ph = new PageHandler(likeCnt, page, 5);
            // 좋아요 한 책 리스트 조회
            List<BookDto> likeBookList = likeService.selectListByPh(memberId, ph);

            model.addAttribute("ph", ph);
            model.addAttribute("likeBookList", likeBookList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/like-list";
    }

    @PostMapping("/myPage/like/deleteAll")
    public String deleteAll(@SessionAttribute Integer userId) {
        try {
            Integer memberId = memberService.selectMember(userId).getMemberId();
            // 회원의 좋아요 모두 삭제
            likeService.deleteAll(memberId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage/like/list";
    }

    @PostMapping("/myPage/like/deleteSelected")
    public String deleteSelected(@RequestParam("bookId") List<Integer> bookIdList, @SessionAttribute Integer userId) {
        try {
            Integer memberId = memberService.selectMember(userId).getMemberId();
            likeService.deleteSelected(memberId, bookIdList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage/like/list";
    }

    // 현재 세션에 로그인 중인 유저의 해당 책에 대한 좋아요를 추가
    @PostMapping("/like/insert")
    @ResponseBody
    public String insertLike(@RequestBody LikeDto likeDto,
                             @SessionAttribute(value = "userId", required = false) Integer userId) throws Exception{
        if(userId == null) return "userNotLogin";

        return likeService.insertLike(likeDto, userId);
    }

    // 현재 세션에 로그인 중인 유저의 해당 책에 대한 좋아요를 삭제
    @PostMapping("/like/delete")
    @ResponseBody
    public String deleteLike(@RequestBody LikeDto likeDto,
                             @SessionAttribute(value = "userId", required = false) Integer userId) throws Exception {
        if(userId == null) return "userNotLogin";

        return likeService.deleteLike(likeDto, userId);
    }
}
