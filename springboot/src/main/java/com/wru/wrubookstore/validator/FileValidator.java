package com.wru.wrubookstore.validator;

import com.wru.wrubookstore.error.exception.InvalidImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    public void validateImage(MultipartFile file){
        // 파일이 있는지 검증
        if(file == null || file.isEmpty()){
            throw new InvalidImageException("ERROR: 이미지를 선택해주세요.");
        }

        // 타입이 일치하는지 검증
        String contentType = file.getContentType();
        if(contentType==null||!contentType.startsWith("image/")){
            throw new InvalidImageException("ERROR: 이미지 파일만 업로드할 수 있습니다.");
        }

        // 5MB보다 큰지 검증
        if(file.getSize() > 5 * 1024 * 1024){
            throw new InvalidImageException("ERROR: 이미지 크기는 최대 5MB 입니다.");
        }
    }
}
