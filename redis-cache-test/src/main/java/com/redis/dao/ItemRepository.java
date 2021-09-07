package com.redis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redis.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	
}