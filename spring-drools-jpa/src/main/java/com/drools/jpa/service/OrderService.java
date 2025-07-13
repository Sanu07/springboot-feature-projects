package com.drools.jpa.service;

import com.drools.jpa.model.CardType;
import com.drools.jpa.model.Order;
import com.drools.jpa.model.OrderRequest;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private KieSession kieSession;

    public Order save(OrderRequest request) {
        if (CardType.HDFC.equals(request.getCardType())) {
            kieSession.getAgenda().getAgendaGroup("orders-discount-hdfc").setFocus();
        } else if (CardType.SBI.equals(request.getCardType())) {
            kieSession.getAgenda().getAgendaGroup("orders-discount-sbi").setFocus();
        } else if (CardType.ICICI.equals(request.getCardType())) {
            kieSession.getAgenda().getAgendaGroup("orders-discount-icici").setFocus();
        }
        Order order = new Order();
        order.setOrderId(request.getOrderId());
        order.setPrice(request.getPrice());
        order.setCardType(request.getCardType());
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.delete(kieSession.getFactHandle(order));
        return order;
    }
}
