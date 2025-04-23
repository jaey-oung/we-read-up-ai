package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.WriterDto;

public class WriterCreateFailException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public WriterCreateFailException(String message, WriterDto writerDto){
        super(message);
        this.debugMessage = "writerDto="+writerDto;
    }
    public String getDebugMessage(){
        return debugMessage;
    }
}
