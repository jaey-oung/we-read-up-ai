package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.FaqDto;
import com.wru.wrubookstore.repository.FaqRepositoryImpl;
import com.wru.wrubookstore.service.FaqServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class FaqController {

    private final FaqServiceImpl faqService;

    public FaqController(FaqServiceImpl faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/faq-list")
    public String list(Model m){
        try {
            List<FaqDto> faqs = faqService.getList();
            m.addAttribute("faqs", faqs);
//            System.out.println("faqs = " + faqs);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "board/faq-list";
    }
}
