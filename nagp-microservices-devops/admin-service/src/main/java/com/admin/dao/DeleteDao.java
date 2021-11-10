package com.admin.dao;

public interface DeleteDao<K> {

	void deleteById(K identifier);
}
