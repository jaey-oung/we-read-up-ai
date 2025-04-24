package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.ReviewDto;

public class ReviewDeleteFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public ReviewDeleteFailException(String message, ReviewDto reviewDto){
        super(message);
        this.debugMessage = "reviewDto="+reviewDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
