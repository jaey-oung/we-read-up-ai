package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.LikeDto;

public class LikeInsertFailedException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public LikeInsertFailedException(String message, LikeDto likeDto){
        super(message);
        this.debugMessage = "LikeDto="+likeDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
