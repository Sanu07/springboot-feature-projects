package com.camunda.pizza.entity;

import com.camunda.pizza.enums.PizzaOrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "pizza_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PizzaOrderStatus status;
    @Column(name = "correlation_key")
    private String correlationKey;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "modified_at")
    private OffsetDateTime modifiedAt;
}
