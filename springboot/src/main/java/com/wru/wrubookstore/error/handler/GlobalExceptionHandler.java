package com.wru.wrubookstore.error.handler;

import com.wru.wrubookstore.error.exception.*;
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

    // 삭제할 좋아요를 찾지 못했을 경우 발생
    @ExceptionHandler(LikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerLikeNotFound(LikeNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 좋아요 추가에 실패할 경우 발생
    @ExceptionHandler(LikeInsertFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerLikeInsertFailed(LikeInsertFailedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 좋아요를 삭제할 때 실패할 경우 발생
    @ExceptionHandler(LikeDeleteFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerLikeDeleteFailed(LikeDeleteFailedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 이미 좋아요를 누른 상품에 좋아요를 추가하려고 할때 발생
    @ExceptionHandler(DuplicateLikeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerDuplicateLike(DuplicateLikeException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
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
