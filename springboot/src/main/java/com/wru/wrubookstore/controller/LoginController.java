package com.wru.wrubookstore.controller;


import com.wru.wrubookstore.domain.FindIdCheck;
import com.wru.wrubookstore.domain.FindPwCheck;
import com.wru.wrubookstore.dto.EmployeeDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import com.wru.wrubookstore.service.EmployeeService;
import com.wru.wrubookstore.service.MemberService;
import com.wru.wrubookstore.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final MemberService memberService;

    LoginController(EmployeeService employeeService, UserService userService, MemberService memberService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.memberService = memberService;
    }

    // 쿠키에 저장된 이메일을 모델에 추가
    @GetMapping("/login")
    public String loginForm(@CookieValue(name = "email", required = false) String rememberedEmail, Model model) {
        model.addAttribute("rememberedEmail", rememberedEmail);
        return "login/login";
    }

    @PostMapping("/login")
    public String login(String email, String password, String redirectUrl, boolean rememberId, RedirectAttributes rattr,
                        HttpServletRequest request, HttpServletResponse response) {

        boolean isFieldNullOrEmpty = email == null || email.isBlank() || password == null || password.isBlank();

        // 로그인 실패 시 이메일, 비밀번호, 메시지를 리다이렉트로 전달
        if (isFieldNullOrEmpty) {
            rattr.addFlashAttribute("email", email);
            rattr.addFlashAttribute("password", password);
            rattr.addFlashAttribute("msg", "EMPTY_FIELD");
            return "redirect:/login/login?redirectUrl=" + redirectUrl;
        }

        // 세션 가져오기
        HttpSession session = request.getSession();

        // 직원 로그인 시도
        EmployeeDto employeeDto = employeeService.login(email, password);
        if (employeeDto != null) {
            session.setAttribute("employeeId", employeeDto.getEmployeeId());
            session.setAttribute("authorityId", employeeDto.getAuthorityId());
            session.setAttribute("name", employeeDto.getName());
        } else {
            // 2. 비회원 또는 회원 로그인 시도
            UserDto userDto = userService.login(email, password);
            if (userDto == null) {
                rattr.addFlashAttribute("msg", "LOGIN_ERR");
                rattr.addFlashAttribute("email", email);
                rattr.addFlashAttribute("password", password);
                return "redirect:/login/login?redirectUrl=" + redirectUrl;
            }
            session.setAttribute("userId", userDto.getUserId());
            session.setAttribute("name", userDto.getName());
            session.setAttribute("isMember", userDto.getIsMember());
        }

        // 쿠키 저장
        Cookie cookie = new Cookie("email", email);

        // ID 기억하기 체크 했을 경우 쿠키 30일 유지
        // ID 기억하기 체크 안했을 경우 쿠키 삭제
        cookie.setMaxAge(!rememberId ? 0 : 60 * 60 * 24 * 30);
        response.addCookie(cookie);

        // 리다이렉트 URL 설정
        redirectUrl = (redirectUrl == null || redirectUrl.isBlank()) ? "/" : redirectUrl;

        return "redirect:"+redirectUrl;
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/findId")
    public String findIdForm(Model model) {
        // 유효성 검사를 위해 비어있는 MemberDto 전송
        model.addAttribute("member", new MemberDto());

        return "login/find-id";
    }

    @PostMapping("/findId")
    public String findId(@Validated(FindIdCheck.class) @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult, Model model) {

        try {
            MemberDto findMemberDto = memberService.selectByNameAndPhoneNum(memberDto);

            if (findMemberDto == null) {
                bindingResult.reject("errorMessage", "일치하는 정보가 없습니다. 입력 정보를 다시 확인해주세요.");
            }

            // 유효성 검사
            if (bindingResult.hasErrors()) {
                model.addAttribute("member", memberDto);

                return "login/find-id";
            }

            model.addAttribute("member", findMemberDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login/find-id-result";
    }

    @GetMapping("/findPw")
    public String findPwForm(Model model) {
        model.addAttribute("member", new MemberDto());

        return "login/find-pw";
    }

    @PostMapping("/findPw")
    public String findPw(@Validated(FindPwCheck.class) @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult, Model model) {
        try {
            MemberDto findMemberDto = memberService.selectByEmailAndNameAndPhoneNum(memberDto);

            if (findMemberDto == null) {
                bindingResult.reject("errorMessage", "일치하는 정보가 없습니다. 입력 정보를 다시 확인해주세요.");
            }

            // 유효성 검사
            if (bindingResult.hasErrors()) {
                model.addAttribute("member", memberDto);

                return "login/find-pw";
            }

            model.addAttribute("member", findMemberDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login/find-pw-result";
    }

}
