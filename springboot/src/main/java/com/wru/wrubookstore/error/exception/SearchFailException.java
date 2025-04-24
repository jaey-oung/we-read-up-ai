package com.wru.wrubookstore.error.exception;

public class SearchFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public SearchFailException(String message, String keyword){
        super(message);
        this.debugMessage = "keyword="+keyword;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
