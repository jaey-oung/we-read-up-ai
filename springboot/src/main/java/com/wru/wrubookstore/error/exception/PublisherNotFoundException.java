package com.wru.wrubookstore.error.exception;

public class PublisherNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public PublisherNotFoundException(Integer bookid){
        super("출판사 정보를 확인할 수 없습니다. 정보가 누락되었거나 일시적인 오류일 수 있습니다.");
        this.debugMessage = "bookId="+bookid;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
