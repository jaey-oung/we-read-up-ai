package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.dto.CartDto;
import com.wru.wrubookstore.dto.response.cart.CartListResponse;
import com.wru.wrubookstore.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 특정 사용자의 전체 장바구니 목록 조회
    @GetMapping("/list")
    public String list(@SessionAttribute(value = "userId", required = false) Integer userId,
                           HttpServletRequest request, Model model) {
        // 로그인 하지 않았다면 로그인 화면으로 이동
        if (userId == null)
            return "redirect:/login/login?redirectUrl="+request.getRequestURL();

        List<CartListResponse> list;

        try {
            list = cartService.selectCartListByUserId(userId);
        } catch (Exception e) {
            list = Collections.emptyList();
        }

        model.addAttribute("list", list);

        return "myPage/cart-list";
    }

    // 특정 사용자의 장바구니에 도서 한 권 추가
    @PostMapping("/add/one")
    @ResponseBody
    public String addOne(@SessionAttribute(value = "userId", required = false) Integer userId,
                         @RequestBody CartDto cartDto) {
        // 로그인 하지 않았다면 로그인이 필요하다는 메시지 반환
        if (userId == null)
            return "LOGIN_REQUIRED";

        cartDto.setUserId(userId);
        cartDto.setQuantity(1);
        try {
            int result = cartService.addOne(cartDto);
            return result == 1 ? "장바구니에 추가되었습니다" : "장바구니에 추가하지 못했습니다";
        } catch (Exception e) {
            return "장바구니 처리 중 오류가 발생했습니다";
        }
    }

    // 특정 사용자의 장바구니에 도서 한 권 또는 여러 권 추가
    @PostMapping("/add/multiple")
    @ResponseBody
    public String addMultiple(@SessionAttribute(value = "userId", required = false) Integer userId,
                              @RequestBody CartDto cartDto) {
        // 로그인 하지 않았다면 로그인이 필요하다는 메시지 반환
        if (userId == null)
            return "LOGIN_REQUIRED";

        cartDto.setUserId(userId);
        try {
            int result = cartService.addMultiple(cartDto);
            return result == 1 ? "장바구니에 추가되었습니다" : "장바구니에 추가하지 못했습니다";
        } catch (Exception e) {
            return "장바구니 처리 중 오류가 발생했습니다";
        }
    }

    // 특정 사용자의 장바구니에서 도서 수량 조절
    @PostMapping("/update")
    @ResponseBody
    public String update(@SessionAttribute(value = "userId", required = false) Integer userId,
                         @RequestBody CartDto cartDto) {
        // 로그인 하지 않았다면 로그인이 필요하다는 메시지 반환
        if (userId == null)
            return "LOGIN_REQUIRED";

        cartDto.setUserId(userId);
        try {
            int result = cartService.update(cartDto);
            return result == 1 ? "장바구니가 성공적으로 수정되었습니다" : "장바구니 수정에 실패했습니다";
        } catch (Exception e) {
            return "장바구니 수정 중 오류가 발생했습니다";
        }
    }

    // 특정 사용자의 장바구니에서 하나의 항목 삭제
    @PostMapping("/remove/one")
    @ResponseBody
    public String removeOne(@SessionAttribute(value = "userId", required = false) Integer userId,
                            @RequestBody CartDto cartDto) {
        // 로그인 하지 않았다면 로그인이 필요하다는 메시지 반환
        if (userId == null)
            return "LOGIN_REQUIRED";

        try {
            int result = cartService.deleteByCartId(cartDto.getCartId(), userId);
            return result == 1 ? "선택한 항목이 삭제되었습니다" : "선택한 항목이 삭제에 실패했습니다";
        } catch (Exception e) {
            return "장바구니 항목 삭제 중 오류가 발생했습니다";
        }
    }

    // 특정 사용자의 장바구니에서 선택된 항목들 삭제
    @PostMapping("/remove/selected")
    @ResponseBody
    public String removeSelected(@SessionAttribute(value = "userId", required = false) Integer userId,
                                 @RequestBody List<Integer> cartIdList) {
        // 로그인 하지 않았다면 로그인이 필요하다는 메시지 반환
        if (userId == null)
            return "LOGIN_REQUIRED";

        try {
            int deletedCount = cartService.deleteBySelectedCartIds(userId, cartIdList);
            return deletedCount > 0 ? "선택한 항목이 삭제되었습니다" : "선택한 항목이 삭제에 실패했습니다";
        } catch (Exception e) {
            return "선택한 항목 삭제 중 오류가 발생했습니다.";
        }
    }

    // 특정 사용자의 장바구니에서 모든 항목 삭제
    @PostMapping("/remove/all")
    @ResponseBody
    public String removeAll(@SessionAttribute(value = "userId", required = false) Integer userId)  {
        if (userId == null)
            return "LOGIN_REQUIRED";

        try {
            int result = cartService.deleteAllByUserId(userId);
            return result > 0 ? "모든 항목이 삭제되었습니다" : "모든 항목 삭제에 실패했습니다";
        } catch (Exception e) {
            return "장바구니 전체 삭제 중 오류가 발생했습니다";
        }
    }
}

