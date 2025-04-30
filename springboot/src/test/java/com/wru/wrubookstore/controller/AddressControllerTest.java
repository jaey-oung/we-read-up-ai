package com.wru.wrubookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wru.wrubookstore.dto.AddressDto;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.service.AddressService;
import com.wru.wrubookstore.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 스프링 컨테이너 모킹
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService addressService;

    @MockitoBean
    private MemberService memberService;

    @Test
    @DisplayName("회원의 주소 리스트 조회 테스트")
    public void addressListTest() throws Exception {
        /* given */
        Integer userId = 29;
        Integer memberId = 29;
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(memberId);
        memberDto.setUserId(userId);

        List<AddressDto> addressDtoList = new ArrayList<>();
        addressDtoList.add(new AddressDto(memberId,"자택","테스트유저","01011112222",11111,"서울시 강남구","테스트 아파트 101동",false));



        /* when */
        Mockito.when(memberService.selectMember(userId)).thenReturn(memberDto);
        Mockito.when(addressService.selectList(memberId)).thenReturn(addressDtoList);




        /* then */
        mockMvc.perform(MockMvcRequestBuilders.get("/myPage/addressList").sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("myPage/address-list"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("addressList"))
                .andExpect(MockMvcResultMatchers.model().attribute("addressList", addressDtoList));
    }

    @Test
    @DisplayName("해당 회원의 주소 추가 테스트")
    public void addAddressTest() throws Exception {
        /* given */
        Integer userId = 29;

        /* when */

        /* then */
        mockMvc.perform(MockMvcRequestBuilders.post("/myPage/addAddress")
                .sessionAttr("userId", userId)
                .param("name", "자택")
                .param("recipient", "홍길동")
                .param("phoneNum", "010-1234-5678")
                .param("zipCode", "12345")
                .param("mainAddress", "서울시 강남구")
                .param("detailAddress", "테스트 아파트")
                .param("defaultAddress", "false"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/myPage/addressList"));
    }
}