package com.wru.wrubookstore.error.exception;

import com.wru.wrubookstore.dto.BookDto;

public class BookAlreadyExistsException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public BookAlreadyExistsException(String message, BookDto bookDto){
        super(message);
        this.debugMessage = "bookDto="+bookDto;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
