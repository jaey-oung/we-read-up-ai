package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.ReviewDto;

public class ReviewModifyFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public ReviewModifyFailException(String message, ReviewDto reviewDto){
        super(message);
        this.debugMessage = "ReviewDto="+reviewDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
