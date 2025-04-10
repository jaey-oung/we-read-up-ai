package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.AllCheck;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.service.MemberService;
import com.wru.wrubookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final MemberService memberService;
    private final UserService userService;

    public ProfileController(MemberService memberService, UserService userService) {
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/myPage/editProfile")
    public String editProfileForm(@SessionAttribute Integer userId, @SessionAttribute boolean isMember, Model model) {
        try {
            if (isMember) {
                MemberDto memberDto = memberService.select(userId);
                model.addAttribute("user", memberDto);
            }
            else {
                UserDto userDto = userService.select(userId);
                model.addAttribute("user", userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/edit-profile";
    }

    @PostMapping("/myPage/editMember")
    public String editMember(@Validated(AllCheck.class) @ModelAttribute("user") MemberDto memberDto, BindingResult bindingResult,
                             @RequestParam String pwConfirm, Model model) {
        // 비밀번호 동일 입력 체크
        if (!pwCheck(memberDto.getPassword(), pwConfirm)) {
            bindingResult.rejectValue("password", "PasswordMismatch", "비밀번호가 다릅니다. 다시 입력해주세요.");
        }
        // 유효성 검사 체크
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", memberDto);

            return "myPage/edit-profile";
        }

        try {
            memberService.editMember(memberDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage";
    }

    @PostMapping("/myPage/editUser")
    public String editUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,
                           @RequestParam String pwConfirm, Model model) {
        // 비밀번호 동일 입력 체크
        if (!pwCheck(userDto.getPassword(), pwConfirm)) {
            bindingResult.rejectValue("password", "PasswordMismatch", "비밀번호가 다릅니다. 다시 입력해주세요.");
        }
        // 유효성 검사 체크
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);

            return "myPage/edit-profile";
        }

        try {
            userService.update(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage";
    }

    @PostMapping("/myPage/withdraw")
    public String withdraw(@SessionAttribute Integer userId, HttpSession session) {
        try {
            // 회원 삭제 시 세션도 함께 삭제
            memberService.withdraw(userId);
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/myPage/convertMember")
    public String convertMemberForm(@SessionAttribute Integer userId, Model model) {
        try {
            UserDto userDto = userService.select(userId);

            model.addAttribute("user", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/convert-member";
    }

    @PostMapping("/myPage/convertMember")
    public String convertMember(@Validated(AllCheck.class) @ModelAttribute("user") MemberDto memberDto, BindingResult bindingResult,
                                @RequestParam String pwConfirm, Model model, HttpSession session) {
        // 비밀번호 동일 입력 체크
        if (!pwCheck(memberDto.getPassword(), pwConfirm)) {
            bindingResult.rejectValue("password", "PasswordMismatch", "비밀번호가 다릅니다. 다시 입력해주세요.");
        }
        // 유효성 검사 체크
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberDto", memberDto);

            return "myPage/convert-member";
        }
        // 회원 가입 성공 시, 세션 삭제 및 회원 생성 후 메인 페이지로 이동
        session.invalidate();
        try {
            memberService.convertToMember(memberDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    private boolean pwCheck(String pw1, String pw2) {
        return pw1.equals(pw2);
    }
}
