package com.wru.wrubookstore.error.exception;

public class BookDeleteFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookDeleteFailException(String message, Integer bookId){
        super(message);
        this.debugMessage = "bookId="+bookId;
    }
    public String getDebugMessage(){
        return debugMessage;
    }

}
