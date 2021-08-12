package com.batch.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.batch.mongo.model.Order;

@Repository(value = "mongoOrderRepository")
public interface OrderRepository extends MongoRepository<Order, String>{

}
