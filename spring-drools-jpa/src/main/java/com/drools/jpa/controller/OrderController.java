package com.drools.jpa.controller;

import com.drools.jpa.model.Order;
import com.drools.jpa.model.OrderRequest;
import com.drools.jpa.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order save(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.save(request);
        log.info("Saved Order {}", savedOrder);
        return savedOrder;
    }
}
