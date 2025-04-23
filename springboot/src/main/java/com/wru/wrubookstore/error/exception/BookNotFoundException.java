package com.wru.wrubookstore.error.exception;


public class BookNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookNotFoundException(String message, Integer bookId) {
        super(message);
        this.debugMessage = "bookId=" + bookId;
    }

    public BookNotFoundException(String message) {
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

}
