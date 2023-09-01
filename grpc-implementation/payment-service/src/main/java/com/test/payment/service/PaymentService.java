package com.test.payment.service;

import com.shared.model.Order;
import com.shared.model.OrderState;
import com.shared.model.OrderStatus;
import com.shared.model.Payment;
import com.test.payment.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    private final PaymentMapper paymentMapper;
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    public void send(Order orderResponse) {
        Payment payment = paymentMapper.convert(orderResponse);
        log.info("saved in payment repo {}", payment);
        log.info("published payment to MQ {}", payment);
        OrderStatus orderStatus = OrderStatus.builder()
                .orderState(OrderState.ORDER_PAID)
                .orderId(orderResponse.getOrderId())
                .message("Successfully paid by " + payment.getPaymentMethod())
                .build();
        log.info("published order status to MQ {}", orderStatus);
    }
}