package com.wru.wrubookstore.error.exception;

public class BookInfoMissingException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookInfoMissingException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
