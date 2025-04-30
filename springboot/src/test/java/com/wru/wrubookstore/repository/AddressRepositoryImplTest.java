package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AddressRepositoryImplTest {

    @Autowired
    private AddressRepository addressRepository;

    Integer testMemberId = 1;
    Integer addressId;
    // 존재하지 않는 addressId
    Integer notExistAddressId = 99999;
    AddressDto houseAddress;

    @BeforeEach
    @DisplayName("DB 정보 삭제 후 2개의 주소 추가")
    void beforeEach() throws Exception {
        addressRepository.deleteAll();

        houseAddress = new AddressDto(
                testMemberId,
                "자택",
                "테스트유저",
                "01011112222",
                11111,
                "서울시 강남구",
                "테스트 아파트 101동",
                false
        );

        AddressDto companyAddress = new AddressDto(
                testMemberId,
                "회사",
                "테스트유저",
                "01033334444",
                22222,
                "서울시 서초구",
                "테스트 빌딩 2층",
                false
        );

        addressRepository.insert(houseAddress);
        addressRepository.insert(companyAddress);

        addressId = houseAddress.getAddressId();
    }

    @Test
    @DisplayName("selectList 테스트")
    public void selectListTest() throws Exception {
        /* given */
        Integer memberId = testMemberId;

        /* when */
        List<AddressDto> addressDtoList = addressRepository.selectList(memberId);

        AddressDto houseAddress = addressDtoList.stream()
                .filter(addressDto -> "자택".equals(addressDto.getName()))
                .findFirst()
                .orElse(null);

        AddressDto companyAddress = addressDtoList.stream()
                .filter(addressDto -> "회사".equals(addressDto.getName()))
                .findFirst()
                .orElse(null);

        /* then */
        // addressDtoList 테스트
        assertNotNull(addressDtoList);
        assertEquals(2, addressDtoList.size());

        // name=자택 인 addressDto 테스트
        assertNotNull(houseAddress);
        assertEquals("테스트유저", houseAddress.getRecipient());
        assertEquals("01011112222", houseAddress.getPhoneNum());
        assertEquals(11111, houseAddress.getZipCode());
        assertEquals("서울시 강남구", houseAddress.getMainAddress());
        assertEquals("테스트 아파트 101동", houseAddress.getDetailAddress());
        assertFalse(houseAddress.isDefaultAddress());

        // name=회사 인 addressDto 테스트
        assertNotNull(companyAddress);
        assertEquals("테스트유저", companyAddress.getRecipient());
        assertEquals("01033334444", companyAddress.getPhoneNum());
        assertEquals(22222, companyAddress.getZipCode());
        assertEquals("서울시 서초구", companyAddress.getMainAddress());
        assertEquals("테스트 빌딩 2층", companyAddress.getDetailAddress());
        assertFalse(companyAddress.isDefaultAddress());
    }

    @Test
    @DisplayName("selectById 테스트")
    public void selectByIdTest() throws Exception {
        /* given */

        /* when */
        AddressDto selectAddressDto = addressRepository.selectById(addressId);
        // 존재하지 않은 addressDto 생성 = null
        AddressDto nullAddressDto = addressRepository.selectById(notExistAddressId);

        /* then */
        assertNotNull(selectAddressDto);
        assertEquals(addressId, selectAddressDto.getAddressId());
        assertEquals("테스트유저", selectAddressDto.getRecipient());
        assertEquals("01011112222", selectAddressDto.getPhoneNum());
        assertEquals(11111, selectAddressDto.getZipCode());
        assertEquals("서울시 강남구", selectAddressDto.getMainAddress());
        assertEquals("테스트 아파트 101동", selectAddressDto.getDetailAddress());
        assertFalse(selectAddressDto.isDefaultAddress());

        assertNull(nullAddressDto);
    }

    @Test
    @DisplayName("Update 테스트")
    public void updateTest() throws Exception {
        /* given */
        String name = "집";

        houseAddress.setName(name);

        // 수정되지 않을 addressDto 생성
        AddressDto notExistAddressDto = new AddressDto();
        notExistAddressDto.setAddressId(notExistAddressId);
        notExistAddressDto.setName("저장되지 않는 주소");

        // recipient가 null인 DTO 생성
        AddressDto nullAddressDto = new AddressDto();
        nullAddressDto.setAddressId(addressId);
        nullAddressDto.setRecipient(null);



        /* when */
        addressRepository.update(houseAddress);
        int zeroUpdateCnt = addressRepository.update(notExistAddressDto);

        AddressDto updateAddressDto = addressRepository.selectById(addressId);



        /* then */
        assertNotNull(updateAddressDto);
        assertEquals(addressId, updateAddressDto.getAddressId());
        assertEquals(name, updateAddressDto.getName());

        // 존재하지 않는 addressId이므로 updateCnt = 0
        assertEquals(0, zeroUpdateCnt);

        // recipient는 not null이므로 에러 처리
        assertThrows(DataIntegrityViolationException.class, () -> {
            addressRepository.update(nullAddressDto);
        });
    }
}