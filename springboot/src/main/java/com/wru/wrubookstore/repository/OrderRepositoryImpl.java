package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.OrderBookDto;
import com.wru.wrubookstore.dto.request.order.OrderBookRequest;
import com.wru.wrubookstore.dto.OrderDto;
import com.wru.wrubookstore.dto.request.order.OrderHistoryRequest;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final String namespace = "com.wru.wrubookstore.mapper.OrderMapper.";
    private final SqlSessionTemplate session;

    public OrderRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    @Override
    public OrderDto select(Integer orderId) throws Exception {
        return session.selectOne(namespace + "select", orderId);
    }

    @Override
    public List<OrderDto> selectList(Map<String, Object> map) throws Exception {
        return session.selectList(namespace + "selectList", map);
    }

    @Override
    public List<OrderHistoryRequest> selectListByOsc(Map<String, Object> map) throws Exception {
        return session.selectList(namespace + "selectListByOsc", map);
    }

    @Override
    public int selectCnt(Integer userId) throws Exception {
        return session.selectOne(namespace + "selectCnt", userId);
    }

    @Override
    public int selectCntByOsc(Map<String, Object> map) throws Exception {
        return session.selectOne(namespace + "selectCntByOsc", map);
    }

    @Override
    public List<OrderBookRequest> selectOrderBook(Integer orderId) throws Exception {
        return session.selectList(namespace + "selectOrderBook", orderId);
    }

    @Override
    public List<Integer> selectBookIdInSalesRank() throws Exception {
        return session.selectList(namespace + "selectBookIdInSalesRank");
    }

    @Override
    public int insertOrder(OrderDto orderDto) throws Exception {
        return session.insert(namespace + "insertOrder", orderDto);
    }

    @Override
    public int insertOrderTest(OrderDto orderDto) throws Exception {
        return session.insert(namespace + "insertOrderTest", orderDto);
    }

    @Override
    public int insertOrderBook(OrderBookDto orderBookDto) throws Exception {
        return session.insert(namespace + "insertOrderBook", orderBookDto);
    }

    @Override
    public int deleteAllOrdersTest() throws Exception {
        return session.delete(namespace + "deleteAllOrdersTest");
    }

    @Override
    public int deleteAllOrderBookTest() throws Exception {
        return session.delete(namespace + "deleteAllOrderBookTest");
    }
}
