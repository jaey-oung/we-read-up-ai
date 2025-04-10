package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.InquiryDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.service.InquiryServiceImpl;
import com.wru.wrubookstore.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/inquiryboard")
public class InquiryController {

    private final InquiryServiceImpl inquiryService;
    private final MemberService memberService;

    public InquiryController(InquiryServiceImpl inquiryService, MemberService memberService) {
        this.inquiryService = inquiryService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public String list(Model m, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        String employeeId = (String) session.getAttribute("employeeId");
        Integer memberId = null;
        String currentUserId = null;

        System.out.println("session userId = " + userId);
        System.out.println("session employeeId = " + employeeId);

        try {
            if (userId != null) {
                MemberDto memberDto = memberService.selectMember(userId);

                if (memberDto != null) {  // memberDto가 null인지 체크
                    memberId = memberDto.getMemberId();
                    System.out.println("inquirycontroller_memberId = " + memberId);
                }
            }

            if (employeeId != null) {
                currentUserId = employeeId;  // 직원이면 employeeId 사용
            } else if (memberId != null) {
                currentUserId = memberId.toString();  // 회원이면 memberId 사용
            } else {
                m.addAttribute("msg", "MEMBER_ONLY"); // 회원만 접근 가능 메시지 추가
                return "board/inquiry-list"; // 회원만 접근 가능 메시지를 화면에 표시
            }

            System.out.println("Controller_currentUserId = " + currentUserId);

            List<InquiryDto> list;
            if (currentUserId.startsWith("emp_")) { // 직원인 경우
                list = inquiryService.getAllList(); // 모든 게시물 조회
            } else {
                list = (memberId != null) ? inquiryService.getList(memberId) : Collections.emptyList(); // 회원 게시물 조회
            }

            System.out.println("Controller_memberId = " + memberId);
            System.out.println("Controller_list = " + list);

            m.addAttribute("list", list);
            m.addAttribute("currentUserId", currentUserId);
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR"); // 에러 메시지 추가
        }
        return "board/inquiry-list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("inquiryId") Integer inquiryId, HttpSession session, RedirectAttributes rdatt) {
        Integer userId = (Integer) session.getAttribute("userId");
        Integer memberId;

        try {
            MemberDto memberDto = memberService.selectMember(userId);
            memberId = memberDto.getMemberId();
            String currentUserId = memberId.toString();
            int rowCnt = inquiryService.remove(inquiryId, memberId);
            if(rowCnt!=1)
                throw new Exception("inquiry remove error");
            rdatt.addFlashAttribute("msg", "DEL_OK");
        } catch (Exception e) {
            e.printStackTrace();
            rdatt.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/inquiryboard/list";
    }

    @GetMapping("/write")
    public String writer(Model m){
        m.addAttribute("mode","new");
        return "board/inquiry-list";         //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/write")
    public String write(@RequestBody InquiryDto inquiryDto, Model m, HttpSession session, RedirectAttributes rdatt){
        Integer userId = (Integer) session.getAttribute("userId");
        String employeeId = (String) session.getAttribute("employeeId");
        if (employeeId == null) {
            employeeId = "emp_4";
        }
        Integer memberId;

        inquiryDto.setEmployeeId(employeeId);
        inquiryDto.setInquiryStatusId("inq_1");

        System.out.println("inquiryDto = " + inquiryDto);
        System.out.println("inquiry_controller_write_employeeId = " + employeeId);

        try {
//            MemberDto memberDto = memberService.selectMember(userId);
//            memberId = memberDto.getMemberId();
//            String currentUserId = memberId.toString();
//            System.out.println("inquiry_contorller_memberId = " + memberId);
//            System.out.println("inquiry_contorller_currentUserId = " + currentUserId);
//            int rowCnt = inquiryService.write(inquiryDto);
//            if(rowCnt!=1)
//                throw new Exception("Write failed");
//            rdatt.addFlashAttribute("msg", "WRT_OK");
            if (userId == null) {
                rdatt.addFlashAttribute("msg", "LOGIN_REQUIRED"); // 로그인 필요 메시지 추가
                return "redirect:/inquiryboard/list";
            }

            MemberDto memberDto = memberService.selectMember(userId);
            if (memberDto == null) {
                rdatt.addFlashAttribute("msg", "MEMBER_NOT_FOUND"); // 멤버를 찾을 수 없음 메시지 추가
                return "redirect:/inquiryboard/list";
            }

            memberId = memberDto.getMemberId();
            inquiryDto.setMemberId(memberId); // inquiryDto에 memberId 설정
            String currentUserId = memberId.toString();
            System.out.println("inquiry_contorller_memberId = " + memberId);
            System.out.println("inquiry_contorller_currentUserId = " + currentUserId);
            int rowCnt = inquiryService.write(inquiryDto);
            if (rowCnt != 1)
                throw new Exception("Write failed");
            rdatt.addFlashAttribute("msg", "WRT_OK");
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("inquiryDto", inquiryDto);
            rdatt.addFlashAttribute("msg", "WRT_ERR");
        }
        return "redirect:/inquiryboard/list";
    }

    @PostMapping("/modify")
    public String modify(@RequestBody InquiryDto inquiryDto, Model m, HttpSession session, RedirectAttributes rdatt){
        String employeeId = (String) session.getAttribute("employeeId");
//        String employeeId = "emp_4";

        inquiryDto.setEmployeeId(employeeId);

        try {
            int rowCnt = inquiryService.reply(inquiryDto);

            if(rowCnt!=1)
                throw new Exception("Modify failed");
            rdatt.addFlashAttribute("msg","MOD_OK");
            // 답변 내용 모델에 추가
            m.addAttribute("replyContent", inquiryDto.getReplyContent());
            return "redirect:/board/inquiry-list";
        } catch (Exception e) {
            e.printStackTrace();
            rdatt.addFlashAttribute("msg","MOD_ERR");
        }
        return "redirect:/inquiryboard/list";
    }
}
