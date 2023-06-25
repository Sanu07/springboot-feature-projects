package com.camunda.pizza.service;

import com.camunda.pizza.entity.PizzaOrder;
import com.camunda.pizza.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<PizzaOrder> getPizzaOrders() {
        return pizzaRepository.findAll();
    }

    public List<PizzaOrder> getPizzaOrderByCorrelationKey(String correlationKey) {
        return pizzaRepository.findByCorrelationKey(correlationKey);
    }

    public PizzaOrder save(PizzaOrder order) {
        PizzaOrder savedOrder = pizzaRepository.save(order);
        return savedOrder;
    }
}
