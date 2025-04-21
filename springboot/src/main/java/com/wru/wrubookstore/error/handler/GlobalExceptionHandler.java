package com.wru.wrubookstore.error.handler;

import com.wru.wrubookstore.error.exception.BookNotFoundException;
import com.wru.wrubookstore.error.exception.MemberNotFoundException;
import com.wru.wrubookstore.error.exception.PublisherNotFoundException;
import com.wru.wrubookstore.error.exception.WriterNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 출판사 정보를 찾지 못했을 때 발생
    @ExceptionHandler(PublisherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerPublisherNotFound(PublisherNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 지은이 정보를 찾지 못했을 때 발생
    @ExceptionHandler(WriterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerWriterNotFound(WriterNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 책 정보를 찾지 못했을 때 발생
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerBookNotFound(BookNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 회원 정보를 찾지 못했을 때 발생
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerMemberNotFound(MemberNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String errorPage() {

        return "error/error";
    }
}
