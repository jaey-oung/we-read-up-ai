package com.wru.wrubookstore.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class FileNameHelper {
    public String getFileName(MultipartFile file){
        return UUID.randomUUID()+"_"+file.getOriginalFilename();
    }
}
