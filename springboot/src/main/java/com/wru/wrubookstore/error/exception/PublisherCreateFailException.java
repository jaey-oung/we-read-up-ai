package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.PublisherDto;

public class PublisherCreateFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public PublisherCreateFailException(String message, PublisherDto publisherDto){
        super(message);
        this.debugMessage = "PublisherDto="+publisherDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
