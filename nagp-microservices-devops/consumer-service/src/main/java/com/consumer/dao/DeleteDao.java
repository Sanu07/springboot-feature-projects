package com.consumer.dao;

public interface DeleteDao<K> {

	void deleteById(K identifier);
}
