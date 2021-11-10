package com.admin.dao;

import java.util.List;

public interface GetAndSaveDao<T, K> {

	List<T> findAll();
	T save(T body);
	T findById(K identifier);
	int getSize();
}
