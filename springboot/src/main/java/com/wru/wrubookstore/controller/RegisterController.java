package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.UserRegisterCheck;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.service.EmployeeService;
import com.wru.wrubookstore.service.MemberService;
import com.wru.wrubookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final MemberService memberService;
    private final EmployeeService employeeService;

    public RegisterController(UserService userService, MemberService memberService, EmployeeService employeeService) {
        this.userService = userService;
        this.memberService = memberService;
        this.employeeService = employeeService;
    }

    @GetMapping("/form")
    public String registerForm(Model model) {
        model.addAttribute("user", new MemberDto());
        return "login/register";
    }

    @PostMapping("/user")
    public String registerUser(@Validated(UserRegisterCheck.class) @ModelAttribute("user") MemberDto memberDto, BindingResult bindingResult,
                               @RequestParam String pwConfirm, RedirectAttributes rattr, Model model) {
        if (!memberDto.getPassword().equals(pwConfirm)) {
            bindingResult.rejectValue("password", "PasswordMismatch", "비밀번호가 다릅니다. 다시 입력해주세요.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", memberDto);
            model.addAttribute("tab", "user");
            return "login/register";
        }

        try {
            UserDto userDto = new UserDto(memberDto.getEmail(), memberDto.getPassword(), memberDto.getName());
            int rowCnt = userService.insert(userDto);

            if(rowCnt != 1)
                throw new Exception("비회원 계정 생성 실패");

            rattr.addFlashAttribute("msg", "USER_SIGNUP_OK");
        } catch (Exception e) {
            model.addAttribute("user", memberDto);
            model.addAttribute("msg", "USER_SIGNUP_ERR");
            return "login/register";
        }
        return "redirect:/";
    }

    @PostMapping("/member")
    public String registerMember(UserDto userDto, @Valid @ModelAttribute("user") MemberDto memberDto,
                                 BindingResult bindingResult, @RequestParam String pwConfirm, RedirectAttributes rattr,
                                 Model model) {
        if (!userDto.getPassword().equals(pwConfirm)) {
            bindingResult.rejectValue("password", "PasswordMismatch", "비밀번호가 다릅니다. 다시 입력해주세요.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", memberDto);
            model.addAttribute("tab", "member");
            return "login/register";
        }

        try {
            memberService.insert(userDto, memberDto);
            rattr.addFlashAttribute("msg", "MEMBER_SIGNUP_OK");
        } catch (Exception e) {
            model.addAttribute("user", memberDto);
            model.addAttribute("msg", "MEMBER_SIGNUP_ERR");
            return "login/register";
        }
        return "redirect:/";
    }

    @PostMapping("/check/email")
    @ResponseBody
    public String checkEmailDuplicate(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            // 직원 테이블 중복 확인
            int result = employeeService.isEmailDuplicated(email.trim());
            if (result == 1)
                return "중복된 이메일입니다";

            // 비회원/회원 테이블 중복 확인
            result = userService.isEmailDuplicated(email.trim());
            return result == 1 ? "중복된 이메일입니다" : "사용 가능한 이메일입니다";
        } catch (Exception e) {
            return "중복확인 처리 중 오류가 발생했습니다";
        }
    }
}
