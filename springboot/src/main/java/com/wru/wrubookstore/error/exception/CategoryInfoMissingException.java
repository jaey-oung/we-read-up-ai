package com.wru.wrubookstore.error.exception;

public class CategoryInfoMissingException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public CategoryInfoMissingException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
