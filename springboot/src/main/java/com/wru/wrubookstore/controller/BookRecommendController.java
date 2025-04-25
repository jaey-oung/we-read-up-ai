package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.request.mbti.MbtiRequestDto;
import com.wru.wrubookstore.dto.response.recommend.BookRecommendResponseDto;
import com.wru.wrubookstore.service.BookRecommendService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRecommendController {

    private final BookRecommendService bookRecommendService;

    // 로그인 여부에 따라 사용자 성향 점수를 활용한 도서 추천
    @PostMapping("/recommend")
    public List<BookRecommendResponseDto> recommend(@RequestBody MbtiRequestDto request, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        return bookRecommendService.recommendBooks(userId, request.getScores());
    }
}
