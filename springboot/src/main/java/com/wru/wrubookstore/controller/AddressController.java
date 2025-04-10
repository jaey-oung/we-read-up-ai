package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.AddressDto;
import com.wru.wrubookstore.service.AddressService;
import com.wru.wrubookstore.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressController {

    private final AddressService addressService;
    private final MemberService memberService;

    public AddressController(AddressService addressService, MemberService memberService) {
        this.addressService = addressService;
        this.memberService = memberService;
    }

    @GetMapping("/myPage/addressList")
    public String addressList(@SessionAttribute Integer userId, Model model) {
        try {
            // userId로 memberId 반환
            Integer memberId = memberService.selectMember(userId).getMemberId();
            // 해당 회원의 주소 목록 조회
            List<AddressDto> addressDtoList = addressService.selectList(memberId);

            model.addAttribute("addressList", addressDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/address-list";
    }

    @GetMapping("/myPage/addAddress")
    public String addAddressForm(Model model) {
        // 유효성 검사를 위해 비어있는 addressDto 생성
        model.addAttribute("address", new AddressDto());

        return "myPage/add-address";
    }

    @PostMapping("/myPage/addAddress")
    public String addAddress(@SessionAttribute Integer userId,
                             @Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult) {
        // 주소 입력 유효성 검사
        if (bindingResult.hasErrors()) {
            return "myPage/add-address";
        }

        try {
            // userId로 memberId 반환
            Integer memberId = memberService.selectMember(userId).getMemberId();
            addressDto.setMemberId(memberId);
            // 해당 주소 DB에 저장
            addressService.insertAddress(addressDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage/addressList";
    }

    @GetMapping("/myPage/editAddress/{addressId}")
    public String updateAddressForm(@PathVariable Integer addressId, Model model) {
        try {
            AddressDto addressDto = addressService.selectOne(addressId);

            model.addAttribute("address", addressDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/edit-address";
    }

    @PostMapping("/myPage/editAddress/{addressId}")
    public String updateAddress(@SessionAttribute Integer userId, Model model,
                                @Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult) {
        // 주소 입력 유효성 검사
        if (bindingResult.hasErrors()) {
            model.addAttribute("address", addressDto);

            return "myPage/edit-address";
        }

        try {
            // userId로 memberId 반환
            Integer memberId = memberService.selectMember(userId).getMemberId();
            addressDto.setMemberId(memberId);
            // 주소 수정
            addressService.updateAddress(addressDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/myPage/addressList";
    }
}
