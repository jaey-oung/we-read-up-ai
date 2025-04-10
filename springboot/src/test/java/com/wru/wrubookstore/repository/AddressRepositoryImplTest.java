package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.AddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressRepositoryImplTest {

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void selectList() throws Exception {
        Integer memberId = 1;

        List<AddressDto> addressDtoList = addressRepository.selectList(memberId);
        AddressDto addressDto = addressDtoList.getFirst();

        assertEquals(addressDto.getName(), "집");
        assertEquals(addressDto.getRecipient(), "김유리");
    }

    @Test
    @Transactional
    public void updateTest() throws Exception {
        Integer addressId = 1;

        AddressDto addressDto = new AddressDto(1, "학원", "김유리", "010-1111-2222", 12345, "서울시 강남구", "역삼동 123-45", false);
        addressDto.setAddressId(addressId);

        int updateCnt = addressRepository.update(addressDto);
        AddressDto updateAddressDto = addressRepository.selectById(addressId);

        assertEquals(updateCnt, 1);
        assertEquals(updateAddressDto.getMemberId(), 1);
        assertEquals(updateAddressDto.getName(), "학원");
    }

    @Test
    public void selectDefaultAddressTest() throws Exception {
        Integer userId = 3;

        AddressDto addressDto = addressRepository.selectDefaultAddress(userId);

        assertEquals(addressDto.getAddressId(), 1);
        assertEquals(addressDto.getName(), "집");
    }
}