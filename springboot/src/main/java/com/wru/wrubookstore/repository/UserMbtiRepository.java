package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.UserMbtiDto;

public interface UserMbtiRepository {
    int insert(UserMbtiDto dto);
    UserMbtiDto selectByUserId(int userId);
    int update(UserMbtiDto dto);
}
