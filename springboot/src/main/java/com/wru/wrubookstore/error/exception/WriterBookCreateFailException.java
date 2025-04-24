package com.wru.wrubookstore.error.exception;

public class WriterBookCreateFailException extends RuntimeException implements DebuggableException {

    private final String debugMessage;

    public WriterBookCreateFailException(String message, Integer bookId){
        super(message);
        this.debugMessage = "bookId="+bookId;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
