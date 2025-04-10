package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.request.MyPageRequest;
import com.wru.wrubookstore.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(required = false) Integer userId, @SessionAttribute(required = false) boolean isMember,
                         HttpServletRequest request, Model model) {
        // 로그인 하지 않았다면 로그인 화면으로 이동
        if(!loginCheck(request))
            return "redirect:/login/login?redirectUrl="+request.getRequestURL();

        // 회원, 비회원을 구별하고 마이페이지에 필요한 정보 출력
        try {
            MyPageRequest myPageRequest = myPageService.selectMyPageInfo(userId, isMember);

            model.addAttribute("myPageRequest", myPageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/myPage/my-page";
    }

    // 세션에 userId 저장되어 있는지 확인
    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("userId") != null;
    }

}
