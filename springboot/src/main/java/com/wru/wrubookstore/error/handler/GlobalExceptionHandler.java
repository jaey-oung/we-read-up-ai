package com.wru.wrubookstore.error.handler;

import com.wru.wrubookstore.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 검색에 실패했을 경우
    @ExceptionHandler(SearchFailException.class)
    @ResponseBody
    public String handlerSearchFail(SearchFailException e) {
        return e.getMessage();
    }
    // 책의 작가 등록에 실패했을 경우
    @ExceptionHandler(WriterBookCreateFailException.class)
    @ResponseBody
    public String handlerWriterBookCreateFail(WriterBookCreateFailException e) {
        return e.getMessage();
    }

    // 책 등록에 실패했을 경우
    @ExceptionHandler(BookCreateFailException.class)
    @ResponseBody
    public String handlerBookCreateFail(BookCreateFailException e) {
        return e.getMessage();
    }
    // 이미 있는 책을 등록하려 했을 경우
    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseBody
    public String handlerBookAlreadyExists(BookAlreadyExistsException e){
        return e.getMessage();
    }
    // 출판사 등록에 실패했을 경우
    @ExceptionHandler(PublisherCreateFailException.class)
    @ResponseBody
    public String handlerPublisherCreateFail(PublisherCreateFailException e) {
        return e.getMessage();
    }

    // 카테고리 요청 정보가 없을 경우
    @ExceptionHandler(CategoryInfoMissingException.class)
    @ResponseBody
    public String handlerCategoryInfoMissing(CategoryInfoMissingException e){
        return e.getMessage();
    }
    // 출판사 요청 정보가 없을 경우
    @ExceptionHandler(PublisherInfoMissingException.class)
    @ResponseBody
    public String handlerPublisherInfoMissing(PublisherInfoMissingException e){
        return e.getMessage();
    }
    // 책 요청 정보가 없을 경우
    @ExceptionHandler(BookInfoMissingException.class)
    @ResponseBody
    public String handlerBookInfoMissing(BookInfoMissingException e){
        return e.getMessage();
    }
    // 지은이 요청 정보가 없을 경우
    @ExceptionHandler(WriterInfoMissingException.class)
    @ResponseBody
    public String handlerWriterInfoMissing(WriterInfoMissingException e) {
        return e.getMessage();
    }
    // 지은이 등록에 실패했을 경우
    @ExceptionHandler(WriterCreateFailException.class)
    @ResponseBody
    public String handlerWriterCreateFail(WriterCreateFailException e) {
        return e.getMessage();
    }
    // 책 재고 추가에 실패했을 경우
    @ExceptionHandler(BookUpdateFailException.class)
    @ResponseBody
    public String handlerBookUpdateFail(BookUpdateFailException e) {
        return e.getMessage();
    }
    // 책 삭제에 실패했을 경우
    @ExceptionHandler(BookDeleteFailException.class)
    @ResponseBody
    public String handlerBookDeleteFail(BookDeleteFailException e) {
        return e.getMessage();
    }
    // 카테고리가 없을 경우
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerCategoryNotFound(CategoryNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 이미지 파일이 없을 경우
    @ExceptionHandler(InvalidImageException.class)
    @ResponseBody
    public String handlerInvalidImage(InvalidImageException e) {
        return e.getMessage();
    }
    // 리뷰 추가에 실패했을 경우 발생
    @ExceptionHandler(ReviewInsertFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerReviewInsertFail(ReviewInsertFailException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 리뷰 삭제에 실패했을 경우 발생
    @ExceptionHandler(ReviewDeleteFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerReviewDeleteFail(ReviewDeleteFailException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 리뷰 수정에 실패했을 경우 발생
    @ExceptionHandler(ReviewModifyFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerReviewModifyFail(ReviewModifyFailException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
    // 세션의 로그인중인 유저의 memberId와 등록된 리뷰의 memberId가 같지 않을경우 발생
    @ExceptionHandler(ReviewAuthorMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerReviewAuthorMismatch(ReviewAuthorMismatchException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/error";
    }
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
