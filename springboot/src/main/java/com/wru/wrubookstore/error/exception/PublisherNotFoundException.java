package com.wru.wrubookstore.error.exception;

public class PublisherNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public PublisherNotFoundException(String message, Integer bookId){
        super(message);
        this.debugMessage = "bookId="+bookId;
    }

    public PublisherNotFoundException(String message){
        super(message);
        this.debugMessage = message;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
