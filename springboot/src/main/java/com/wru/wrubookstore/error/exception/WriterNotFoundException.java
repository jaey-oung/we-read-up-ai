package com.wru.wrubookstore.error.exception;

public class WriterNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public WriterNotFoundException(String message, Integer bookId){
        super(message);
        this.debugMessage = "bookId="+bookId;
    }

    public WriterNotFoundException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
