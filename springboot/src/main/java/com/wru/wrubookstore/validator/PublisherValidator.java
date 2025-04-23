package com.wru.wrubookstore.validator;

import org.springframework.stereotype.Component;

@Component
public class PublisherValidator {

    // 사업자 번호 포맷팅 메서드
    public String getFormatBizRegNo(String bizRegNo){
        if(!(bizRegNo.contains("-")) && bizRegNo.length() == 10){
            return String.format("%s-%s-%s",
                    bizRegNo.substring(0,3),
                    bizRegNo.substring(3,5),
                    bizRegNo.substring(5));
        }
        return bizRegNo;
    }
    // 핸드폰 번호 포맷팅 메서드
    public String getFormatPhoneNumber(String phoneNum){
        if(!(phoneNum.contains("-")) && phoneNum.length() == 11){
            return String.format("%s-%s-%s",
                    phoneNum.substring(0,3),
                    phoneNum.substring(3,7),
                    phoneNum.substring(7));
        }
        return phoneNum;
    }
}
