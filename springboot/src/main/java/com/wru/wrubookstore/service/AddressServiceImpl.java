package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.AddressDto;
import com.wru.wrubookstore.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDto> selectList(Integer memberId) throws Exception {
        return addressRepository.selectList(memberId);
    }

    @Override
    public AddressDto selectOne(Integer addressId) throws Exception {
        return addressRepository.selectById(addressId);
    }

    @Override
    @Transactional
    public int insertAddress(AddressDto addressDto) throws Exception {
        // 만약 새로운 주소가 기본 배송지로 설정되어 있다면, 해당 회원의 나머지 주소는 기본 배송지 뺴기
        if (addressDto.isDefaultAddress()) {
            addressRepository.unsetDefaultAddress(addressDto.getMemberId());
        }

        return addressRepository.insert(addressDto);
    }

    @Override
    @Transactional
    public int updateAddress(AddressDto addressDto) throws Exception {
        if (addressDto.isDefaultAddress()) {
            addressRepository.unsetDefaultAddress(addressDto.getMemberId());
        }

        return addressRepository.update(addressDto);
    }
}
