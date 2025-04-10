package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.OrderSearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.request.order.OrderDetailRequest;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;
import com.wru.wrubookstore.dto.request.order.OrderPaymentRequest;
import com.wru.wrubookstore.dto.response.order.OrderPaymentResponse;
import com.wru.wrubookstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, PaymentRepository paymentRepository, DeliveryRepository deliveryRepository, AddressRepository addressRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.deliveryRepository = deliveryRepository;
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;

    }

    @Override
    public List<OrderHistoryRequest> selectListByOsc(Integer userId, OrderSearchCondition osc) throws Exception {
        return orderRepository.selectListByOsc(Map.of("userId", userId, "osc", osc));
    }

    @Override
    public int selectCntByOsc(Integer userId, OrderSearchCondition osc) throws Exception {
        return orderRepository.selectCntByOsc(Map.of("userId", userId, "osc", osc));
    }

    @Override
    public OrderDetailRequest selectOrderDetail(Integer orderId) throws Exception {
        OrderDto orderDto = orderRepository.select(orderId);
        PaymentDto paymentDto = paymentRepository.select(orderId);
        List<OrderBookRequest> orderBookRequestList = orderRepository.selectOrderBook(orderId);
        DeliveryDto deliveryDto = deliveryRepository.select(orderId);

        return new OrderDetailRequest(orderDto, paymentDto, orderBookRequestList, deliveryDto);
    }

    @Override
    public OrderPaymentRequest selectOrderPayment(Integer userId, OrderPaymentRequest orderPaymentRequest) throws Exception {
        // 주소 정보 가져오기
        AddressDto addressDto = addressRepository.selectDefaultAddress(userId);
        int mileage = 0;

        if(userRepository.selectUser(userId).getIsMember()){
            mileage = memberRepository.selectMember(userId).getMileage();
        }

        // orderPaymentRequest에 주소, 보유 마일리지 정보 추가
        orderPaymentRequest.setAddressDto(addressDto);
        orderPaymentRequest.setMileage(mileage);

        return orderPaymentRequest;
    }

    @Override
    @Transactional
    public void processOrder(OrderPaymentResponse orderPaymentResponse) throws Exception {
        /* 주문 추가 */
        OrderDto orderDto = orderPaymentResponse.getOrderDto();
        orderRepository.insertOrder(orderDto);
        // 추가한 주문의 order_id 조회
        Integer orderId = orderDto.getOrderId();


        /* 주문_도서 추가 */
        List<OrderBookDto> orderBookDtoList = orderPaymentResponse.getOrderBookDtoList();

        for (OrderBookDto orderBookDto : orderBookDtoList) {
            // orderId 설정
            orderBookDto.setOrderId(orderId);
            orderRepository.insertOrderBook(orderBookDto);
        }


        /* 배송 추가 */
        DeliveryDto deliveryDto = orderPaymentResponse.getDeliveryDto();
        // orderId 설정
        deliveryDto.setOrderId(orderId);
        deliveryRepository.insert(deliveryDto);


        /* 결제 추가 */
        PaymentDto paymentDto = orderPaymentResponse.getPaymentDto();
        // orderId 설정
        paymentDto.setOrderId(orderId);
        paymentRepository.insert(paymentDto);


        // 회원 마일리지 추가, 사용 마일리지 차감, 전월 구매금액 추가
        Map<String, Integer> map = new HashMap<>();
        map.put("userId", orderDto.getUserId());
        map.put("mileageDiscount", paymentDto.getMileageDiscount());
        map.put("actualPrice", paymentDto.getActualPrice());
        map.put("totalPrice", paymentDto.getTotalPrice());

        memberRepository.updateMileage(map);
        memberRepository.updateLastMonthAmount(map);
    }
}
