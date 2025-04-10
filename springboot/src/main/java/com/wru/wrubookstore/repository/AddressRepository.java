package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.AddressDto;

import java.util.List;

public interface AddressRepository {

    /* select */
    // 회원 배송지 리스트 조회
    List<AddressDto> selectList(Integer memberId) throws Exception;
    // AddressId로 배송지 조회
    AddressDto selectById(Integer addressId) throws Exception;
    // 회원의 기본 배송지 조회
    AddressDto selectDefaultAddress(Integer userId) throws Exception;

    /* insert */
    int insert(AddressDto addressDto) throws Exception;

    /* update */
    int update(AddressDto addressDto) throws Exception;
    // 해당 회원의 기본 배송지 해제
    int unsetDefaultAddress(Integer memberId) throws Exception;
}
