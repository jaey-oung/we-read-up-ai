package com.wru.wrubookstore.error.exception;

public class BookUpdateFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookUpdateFailException(String message, Integer bookId){
        super(message);
        this.debugMessage = "bookId="+bookId;
    }
    public String getDebugMessage(){
        return debugMessage;
    }
}
