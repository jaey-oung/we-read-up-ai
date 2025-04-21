package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.LikeDto;
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

    @PostMapping("/book/like")
    @ResponseBody
    public String likeList(@RequestBody LikeDto likeDto,
                           @SessionAttribute(value = "userId", required = false) Integer userId,
                           HttpSession session, Model m){
        // book-detail.html에서 like를 눌러서 bookId와 session에 등록된 id를 넘겨주면
        // [세션에 등록된 id에서 member_id를 조인해와서]
        // 검증... like에 member_id중 book_id가 이미 있다면 like 삭제
        // like에 member_id중 book_id가 없다면 like추가

        try{
            int likeStatus = likeService.selectLikeMember(likeDto.getBookId(), userId);
            m.addAttribute("likeStatus", likeStatus);

            // member_id가 book_id를 like하지 않음 - like 추가
            if (likeStatus == 0) {
                likeService.insertLike(likeDto);
            } else {
                likeService.deleteLike(likeDto);
            }

            return "success";

        } catch(Exception e){
            e.printStackTrace();
            return "error";
        }

    }
}
