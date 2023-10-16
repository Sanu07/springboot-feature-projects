package com.test.order.service;

import com.shared.model.Order;
import com.test.order.mapper.OrderMapper;
import com.test.shared.CreateOrderRequest;
import com.test.shared.GetOrderRequest;
import com.test.shared.OrderResponse;
import com.test.shared.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        Order order = orderMapper.convert(request);

        log.info("order saved in repo: {}", order);
        log.info("order published in queue: {}", order);

        OrderResponse orderResponse = orderMapper.convert(order);

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrder(GetOrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        Order orderById = Order.builder().orderId("O1").orderStatus("ORDER_CREATED").deliveryLocation("WB").build();

        OrderResponse orderResponse = orderMapper.convert(orderById);

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();

    }
}