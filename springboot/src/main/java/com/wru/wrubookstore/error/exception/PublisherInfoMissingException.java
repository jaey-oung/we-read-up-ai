package com.wru.wrubookstore.error.exception;

public class PublisherInfoMissingException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public PublisherInfoMissingException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
