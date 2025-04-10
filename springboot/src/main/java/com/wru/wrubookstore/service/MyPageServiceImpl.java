package com.wru.wrubookstore.service;

import com.wru.wrubookstore.domain.OrderStatus;
import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.MembershipDto;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.request.MyPageRequest;
import com.wru.wrubookstore.repository.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final ExchangeRepository exchangeRepository;
    private final RefundRepository refundRepository;

    public MyPageServiceImpl(MemberRepository memberRepository, MembershipRepository membershipRepository, CouponRepository couponRepository, OrderRepository orderRepository, ExchangeRepository exchangeRepository, RefundRepository refundRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.couponRepository = couponRepository;
        this.orderRepository = orderRepository;
        this.exchangeRepository = exchangeRepository;
        this.refundRepository = refundRepository;
    }

    @Override
    public MyPageRequest selectMyPageInfo(Integer userId, boolean isMember) throws Exception {
        MemberDto memberDto = memberRepository.selectMember(userId);                                // 회원 정보
        MembershipDto membershipDto = membershipRepository.select(userId);                          // 멤버십 정보
        int couponCnt = couponRepository.selectCount(userId);                                       // 쿠폰 개수
        int orderCnt = orderRepository.selectCnt(userId);                                           // 주문 개수
        List<OrderDto> orderDtoList = orderRepository.selectList(getSixMonthSummaryMap(userId));    // 주문 리스트
        EnumMap<OrderStatus, Integer> orderCountMap = getOrderCountByStatus(orderDtoList);          // 주문 상태에 따른 주문 개수 Map
        int exchangeCnt = exchangeRepository.selectCnt(userId);                                     // 교환 개수
        int refundCnt = refundRepository.selectCnt(userId);                                         // 환불 개수

        // 회원과 비회원 출력 정보가 다름
        if (isMember) {
            return new MyPageRequest(memberDto.getImage(), membershipDto.getName(), memberDto.getMileage(), couponCnt, orderCnt, orderCountMap.get(OrderStatus.DS1), orderCountMap.get(OrderStatus.DS2), orderCountMap.get(OrderStatus.DS3), exchangeCnt, refundCnt);
        }
        else {
            return new MyPageRequest(orderCnt, orderCountMap.get(OrderStatus.DS1), orderCountMap.get(OrderStatus.DS2), orderCountMap.get(OrderStatus.DS3), exchangeCnt, refundCnt);
        }
    }

    // 주문 상태에 따른 주문 개수 리스트 출력
    private static EnumMap<OrderStatus, Integer> getOrderCountByStatus(List<OrderDto> orderDtoList) {
        EnumMap<OrderStatus, Integer> countMap = new EnumMap<>(OrderStatus.class);

        // 초기화
        for (OrderStatus status : OrderStatus.values()) {
            countMap.put(status, 0);
        }

        for (OrderDto dto : orderDtoList) {
            OrderStatus status = dto.getStatusId();
            countMap.put(status, countMap.get(status) + 1);
        }

        return countMap;
    }

    // userId, startDate, endDate 6개월 차이로 만드는 Map 출력
    private static Map<String, Object> getSixMonthSummaryMap(Integer userId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date startDate = new Date(cal.getTimeInMillis());
        Date endDate = new Date();

        return Map.of("userId", userId, "startDate", df.format(startDate), "endDate", df.format(endDate));
    }
}
