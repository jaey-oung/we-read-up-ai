package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.ReviewDto;

public class ReviewAuthorMismatchException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public ReviewAuthorMismatchException(String message, ReviewDto reviewDto, Integer memberId){
        super(message);
        this.debugMessage = "ReviewDto="+reviewDto+" MemberId="+memberId;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
