package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.OrderSearchCondition;
import com.wru.wrubookstore.dto.*;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.request.order.OrderDetailRequest;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;
import com.wru.wrubookstore.dto.request.order.OrderPaymentRequest;
import com.wru.wrubookstore.dto.response.embedding.EmbeddingResponseDto;
import com.wru.wrubookstore.dto.response.order.OrderPaymentResponse;
import com.wru.wrubookstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final EmbeddingService embeddingService;

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final UserMbtiRepository userMbtiRepository;

    public OrderServiceImpl(EmbeddingService embeddingService, OrderRepository orderRepository,
                            UserRepository userRepository, PaymentRepository paymentRepository,
                            DeliveryRepository deliveryRepository, AddressRepository addressRepository,
                            MemberRepository memberRepository, UserMbtiRepository userMbtiRepository) {
        this.embeddingService = embeddingService;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.deliveryRepository = deliveryRepository;
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.userMbtiRepository = userMbtiRepository;
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

        // 현재 주문에 포함된 모든 도서의 ID 목록 추출
        List<Integer> bookIds = orderBookDtoList.stream()
                .map(OrderBookDto::getBookId)
                .distinct() // 중복 제거
                .collect(Collectors.toList());

        // FastAPI 호출 → 도서 리스트의 평균 임베딩(MBTI 점수) 계산
        EmbeddingResponseDto avgEmbed = embeddingService.getAverageEmbedding(bookIds);

        // 계산된 평균 임베딩 값을 기반으로 UserMbtiDto 생성
        UserMbtiDto userMbtiDto = new UserMbtiDto(
                orderDto.getUserId(),
                avgEmbed.getMbtiS(), avgEmbed.getMbtiI(), avgEmbed.getMbtiF(), avgEmbed.getMbtiD(),
                avgEmbed.getMbtiN(), avgEmbed.getMbtiM(), avgEmbed.getMbtiQ(), avgEmbed.getMbtiA()
        );

        // 기존 사용자 성향 정보가 존재하는지 확인
        UserMbtiDto existing = userMbtiRepository.selectByUserId(orderDto.getUserId());
        if (existing == null) { // 기존 데이터가 없으면 새로 삽입
            userMbtiRepository.insert(userMbtiDto);
        } else { // 기존 데이터가 있다면, 평균을 내어 업데이트
            UserMbtiDto averaged = new UserMbtiDto(
                orderDto.getUserId(),
                existing.getMbtiS().add(avgEmbed.getMbtiS()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiI().add(avgEmbed.getMbtiI()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiF().add(avgEmbed.getMbtiF()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiD().add(avgEmbed.getMbtiD()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiN().add(avgEmbed.getMbtiN()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiM().add(avgEmbed.getMbtiM()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiQ().add(avgEmbed.getMbtiQ()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP),
                existing.getMbtiA().add(avgEmbed.getMbtiA()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP)
            );

            userMbtiRepository.update(averaged);
        }
    }
}
