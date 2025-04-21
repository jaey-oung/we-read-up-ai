package com.wru.wrubookstore.error.exception;

public class WriterNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public WriterNotFoundException(Integer bookid){
        super("도서의 저자 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");
        this.debugMessage = "bookId="+bookid;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
