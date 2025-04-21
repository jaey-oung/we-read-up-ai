package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.LikeDto;

public class DuplicateLikeException extends RuntimeException implements DebuggableException {

    private final String debugMessage;

    public DuplicateLikeException(String message, LikeDto likeDto){
        super(message);
        this.debugMessage = "LikeDto="+likeDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}

