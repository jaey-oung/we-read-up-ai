package com.wru.wrubookstore.error.exception;


public class BookNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookNotFoundException(Integer bookId) {
        super("요청하신 도서를 찾을 수 없습니다. 도서가 삭제되었거나 존재하지 않는 번호일 수 있습니다.");
        this.debugMessage = "bookId=" + bookId;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

}
