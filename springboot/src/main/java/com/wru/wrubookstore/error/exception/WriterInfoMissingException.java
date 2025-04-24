package com.wru.wrubookstore.error.exception;

public class WriterInfoMissingException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public WriterInfoMissingException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
