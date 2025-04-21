package com.wru.wrubookstore.error.exception;

public class MemberNotFoundException extends RuntimeException implements DebuggableException{

    private final String debugMessage;

    public MemberNotFoundException(Integer userId){
        super("회원 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");
        this.debugMessage = "userId="+userId;
    }

    public String getDebugMessage(){
        return debugMessage;
    }
}
