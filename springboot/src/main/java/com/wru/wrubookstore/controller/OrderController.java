package com.wru.wrubookstore.controller;

import com.wru.wrubookstore.domain.OrderSearchCondition;
import com.wru.wrubookstore.domain.PageHandler;
import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.request.order.OrderDetailRequest;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;
import com.wru.wrubookstore.dto.request.order.OrderPaymentRequest;
import com.wru.wrubookstore.dto.response.cart.CartListResponse;
import com.wru.wrubookstore.dto.response.order.OrderPaymentResponse;
import com.wru.wrubookstore.service.CartService;
import com.wru.wrubookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService)
    {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/myPage/orderList")
    public String orderList(@SessionAttribute Integer userId, Model model, @ModelAttribute OrderSearchCondition osc) {
        try {
            // 주문 상태와 날짜 조건에 따른 총 게시물 개수
            int orderCnt = orderService.selectCntByOsc(userId, osc);
            // PageHandler 생성
            PageHandler ph = new PageHandler(orderCnt, osc.getPage(), 4);
            // 검색 조건에 offset 설정
            osc.setOffset(osc.getPageSize() * (osc.getPage() - 1));
            // 주문 상태와 날짜 조건 페이징에 따른 게시물 리스트
            List<OrderHistoryRequest> orderHistoryRequest = orderService.selectListByOsc(userId, osc);

            model.addAttribute("orderList", orderHistoryRequest);
            model.addAttribute("osc", osc);
            model.addAttribute("ph", ph);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/order-list";
    }

    @GetMapping("/myPage/orderDetail/{orderId}")
    public String orderDetail(@PathVariable Integer orderId, Model model) {
        try {
            // 주문 상세 정보를 위한 Dto 생성
            OrderDetailRequest orderDetailRequest = orderService.selectOrderDetail(orderId);

            model.addAttribute("orderDetail", orderDetailRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "myPage/order-detail";
    }

    @GetMapping("/myPage/exchangeRefundList")
    public String exchangeRefundList() {

        return "myPage/exchange-refund-list";
    }

    @PostMapping("/orderForm")
    public String orderPaymentForm(@SessionAttribute Integer userId, @ModelAttribute OrderPaymentRequest orderPaymentRequest,
                         Model model) {

        try {
            System.out.println("userId = " + userId);
            System.out.println("오더//orderPaymentRequest = " + orderPaymentRequest);
            model.addAttribute("orderPaymentRequest", orderService.selectOrderPayment(userId, orderPaymentRequest));
            model.addAttribute("order", orderPaymentRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "order/order";
    }

    @PostMapping("/order")
    public String orderPayment(@ModelAttribute OrderPaymentResponse orderPaymentResponse) {

        try {
            System.out.println("구매!//orderPaymentResponse = " + orderPaymentResponse);

            for(OrderBookDto dto : orderPaymentResponse.getOrderBookDtoList()){
                if(dto.getCartId() != null){
                    cartService.deleteByCartId(dto.getCartId(), orderPaymentResponse.getOrderDto().getUserId());
                }
            }
            orderService.processOrder(orderPaymentResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
