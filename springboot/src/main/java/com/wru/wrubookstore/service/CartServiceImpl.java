package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.CartDto;
import com.wru.wrubookstore.dto.response.cart.CartListResponse;
import com.wru.wrubookstore.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public int countAllByAdmin() throws Exception {
        return cartRepository.countAllByAdmin();
    }

    @Override
    public List<CartDto> selectAllByAdmin() throws Exception {
        return cartRepository.selectAllByAdmin();
    }

    @Override
    public void deleteAllByAdmin() throws Exception {
        cartRepository.deleteAllByAdmin();
    }

    @Override
    public int countAllByUserId(int userId) throws Exception {
        return cartRepository.countAllByUserId(userId);
    }

    @Override
    public List<CartDto> selectAllByUserId(int userId) throws Exception {
        return cartRepository.selectAllByUserId(userId);
    }

    @Override
    public List<CartListResponse> selectCartListByUserId(int userId) throws Exception {
        return cartRepository.selectCartListByUserId(userId);
    }

    @Override
    public CartListResponse selectCartByCartId(int cartId) throws Exception {
        return cartRepository.selectCartByCartId(cartId);
    }

    @Override
    public CartDto selectByUserIdAndBookId(int userId, int bookId) throws Exception {
        return cartRepository.selectByUserIdAndBookId(userId, bookId);
    }

    @Override
    public int addOne(CartDto cartDto) throws Exception {
        CartDto existing = cartRepository.selectByUserIdAndBookId(cartDto.getUserId(), cartDto.getBookId());

        if (existing != null) {
            // 기존 수량에 1 더하기
            existing.setQuantity(existing.getQuantity() + 1);
            existing.setPrice(existing.getPrice() + cartDto.getPrice());
            return cartRepository.update(existing);
        }

        return cartRepository.insert(cartDto);
    }

    @Override
    public int addMultiple(CartDto cartDto) throws Exception {
        CartDto existing = cartRepository.selectByUserIdAndBookId(cartDto.getUserId(), cartDto.getBookId());

        if (existing != null) {
            // 기존 수량과 기존 가격에 현재 수량과 가격 더하기
            existing.setQuantity(existing.getQuantity() + cartDto.getQuantity());
            existing.setPrice(existing.getPrice() + (cartDto.getPrice() * cartDto.getQuantity()));
            return cartRepository.update(existing);
        }

        return cartRepository.insert(cartDto);
    }

    @Override
    public int update(CartDto cartDto) throws Exception {
        return cartRepository.update(cartDto);
    }

    @Override
    public int deleteByCartId(int cartId, int userId) throws Exception {
        return cartRepository.deleteByCartId(cartId, userId);
    }

    @Override
    public int deleteBySelectedCartIds(int userId, List<Integer> cartIdList) throws Exception {
        return cartRepository.deleteBySelectedCartIds(userId, cartIdList);
    }

    @Override
    public int deleteAllByUserId(int userId) throws Exception {
        return cartRepository.deleteAllByUserId(userId);
    }

}
