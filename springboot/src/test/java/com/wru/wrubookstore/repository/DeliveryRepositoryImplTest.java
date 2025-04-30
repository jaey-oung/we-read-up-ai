package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.DeliveryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeliveryRepositoryImplTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    Integer orderId = 1;

    @BeforeEach
    @DisplayName("배송 전체 삭제 후 배송 2개 추가")
    public void beforeEach() throws Exception {
        int deleteCnt = deliveryRepository.deleteAll();

        DeliveryDto deliveryDto1 = new DeliveryDto(
                orderId,
                "홍길동",
                "010-1111-2222",
                11111,
                "서울시 강남구",
                "미왕빌딩 10층",
                "엘레베이터 앞에 놔주세요."
        );

        DeliveryDto deliveryDto2 = new DeliveryDto(
                orderId + 1,
                "이자바",
                "010-9999-8888",
                99999,
                "대전시 동구",
                "홍도로 47",
                "문 앞에 놔주세요."
        );

        deliveryRepository.insert(deliveryDto1);
        deliveryRepository.insert(deliveryDto2);
    }

    @Test
    @DisplayName("배송 조회 및 존재하지 않는 orderId 조회 테스트")
    public void selectTest() throws Exception {
        /* given */
        // 존재하지 않는 orderId
        Integer notExistOrderId = 99999;

        /* when */
        DeliveryDto selectDeliveryDto = deliveryRepository.select(orderId);

        DeliveryDto nullDeliveryDto = deliveryRepository.select(notExistOrderId);

        /* then */
        assertNotNull(selectDeliveryDto);
        assertEquals(orderId, selectDeliveryDto.getOrderId());
        assertEquals("홍길동", selectDeliveryDto.getRecipient());
        assertEquals("010-1111-2222", selectDeliveryDto.getPhoneNum());
        assertEquals(11111, selectDeliveryDto.getZipCode());
        assertEquals("서울시 강남구", selectDeliveryDto.getMainAddress());
        assertEquals("미왕빌딩 10층", selectDeliveryDto.getDetailAddress());
        assertEquals("엘레베이터 앞에 놔주세요.", selectDeliveryDto.getMessage());

        assertNull(nullDeliveryDto);
    }
}