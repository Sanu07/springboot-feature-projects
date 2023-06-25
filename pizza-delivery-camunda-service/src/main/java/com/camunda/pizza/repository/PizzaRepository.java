package com.camunda.pizza.repository;

import com.camunda.pizza.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<PizzaOrder, Long> {
    List<PizzaOrder> findByCorrelationKey(String correlationKey);
}
