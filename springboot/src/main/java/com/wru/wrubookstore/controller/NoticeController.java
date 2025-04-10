package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.CommentDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.NoticeDto;
import com.wru.wrubookstore.domain.NoticepageHandler;
import com.wru.wrubookstore.domain.SearchCondition;
import com.wru.wrubookstore.service.MemberService;
import com.wru.wrubookstore.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    public NoticeController(NoticeService noticeService, MemberService memberService) {
        this.noticeService = noticeService;
        this.memberService = memberService;
    }

    @PostMapping("/modify")
    public String modify(NoticeDto noticeDto, Model m, HttpSession session, RedirectAttributes rdatt){
        String employee_id = (String) session.getAttribute("employeeId");
//        String employee_id = "emp_1";
        noticeDto.setEmployeeId(employee_id);
        try {
            int rowCnt = noticeService.modify(noticeDto);  // insert

            if(rowCnt!=1)
                throw new Exception("Modify failed");

            rdatt.addFlashAttribute("msg", "MOD_OK");   // 세션을 활용한 일회성 저장
            return "redirect:/board/notice-list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto", noticeDto);
            m.addAttribute("msg", "MOD_ERR");
            return "board/notice-detail";
        }
    }

    @GetMapping("/write")
    public String writer(Model m){
//        System.out.println("글쓰기 페이지 진입!");
        m.addAttribute("mode","new");
        return "board/notice-detail";         //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/write")
    public String write(NoticeDto noticeDto, Model m, HttpSession session, RedirectAttributes rdatt){
        String employeeId = (String) session.getAttribute("employeeId");
//        String employee_id = "emp_1";
        noticeDto.setEmployeeId(employeeId);

        try {
            int rowCnt = noticeService.write(noticeDto);  // insert

//            System.out.println("noticeDto: " + noticeDto);

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rdatt.addFlashAttribute("msg", "WRT_OK");   // 세션을 활용한 일회성 저장
            return "redirect:/board/notice-list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("noticeDto", noticeDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board/notice-detail";
        }
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("noticeId")Integer noticeId, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rdatt) {
        String employeeId = (String) session.getAttribute("employeeId");
//        String employeeId = "emp_1";
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

//            System.out.println("notice_id = " + noticeId);
//            System.out.println("employee_id = " + employeeId);

            int rowCnt = noticeService.remove(noticeId, employeeId);

            if(rowCnt!=1)
                throw new Exception("board remove error");

            rdatt.addFlashAttribute("msg", "DEL_OK");

        } catch (Exception e) {
            e.printStackTrace();
            rdatt.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/board/notice-list";
    }

    @GetMapping("/notice-detail")
    public String read(@RequestParam("notice_id")Integer notice_id, Integer page, Integer pageSize, Model m,HttpSession session) {
        try {
            NoticeDto noticeDto = noticeService.read(notice_id);
            String employeeId = (String) session.getAttribute("employeeId");
            Integer userId = (Integer) session.getAttribute("userId");
            noticeDto.setEmployeeId(employeeId);
            MemberDto memberDto = memberService.selectMember(userId);

            System.out.println("\n\n멤버!!///memberDto = " + memberDto);

//            System.out.println("noticeDto: " + noticeDto);  // 로그 확인

            m.addAttribute("noticeDto", noticeDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("mode", "read"); // mode 추가
            m.addAttribute("employeeId", employeeId);
            m.addAttribute("userId", userId);
            m.addAttribute("member", memberDto);

            // searchCondition 추가
            SearchCondition sc = new SearchCondition(page, pageSize);
            m.addAttribute("searchCondition", sc);
        } catch (Exception e) {
            e.printStackTrace();
            return "board/notice-list";
        }
        return "board/notice-detail";
    }

    @GetMapping("/notice-list")
    public String list(@ModelAttribute SearchCondition sc, Model m, HttpServletRequest request, HttpSession session, NoticeDto noticeDto) {
//        if(!loginCheck(request))
//            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동
        String employeeId = (String) session.getAttribute("employeeId");
        noticeDto.setEmployeeId(employeeId);
        System.out.println("employeeId = " + employeeId);
        try {
            int totalCnt = noticeService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            NoticepageHandler noticepageHandler = new NoticepageHandler(totalCnt, sc);

            List<NoticeDto> list = noticeService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", noticepageHandler);
            m.addAttribute("employeeId", employeeId);

            System.out.println("Notice List: " + list);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt",0);
        }
        return "board/notice-list"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }


    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }

}