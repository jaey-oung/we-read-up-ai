//package com.wru.wrubookstore.service;
//
//import com.wru.wrubookstore.dto.AddressDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class AddressServiceImplTest {
//
//    @Autowired
//    AddressService addressService;
//
//    @Test
//    @Transactional
//    public void insertAddressTest() throws Exception {
//        Integer memberId = 1;
//
//        AddressDto addressDto = new AddressDto(memberId, "회사", "홍길동", "010-1111-2222", 12345, "서울시 강남구", "미왕빌딩 10층", true);
//
//        int insertCnt = addressService.insertAddress(addressDto);
//        List<AddressDto> addressDtoList = addressService.selectList(memberId);
//        AddressDto changeAddressDto = addressDtoList.getFirst();
//        AddressDto insertAddressDto = addressDtoList.getLast();
//
//        assertFalse(changeAddressDto.isDefaultAddress());
//        assertTrue(insertAddressDto.isDefaultAddress());
//    }
//}