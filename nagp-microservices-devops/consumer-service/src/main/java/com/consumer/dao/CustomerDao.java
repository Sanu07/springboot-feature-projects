package com.consumer.dao;

import com.consumer.model.Customer;

public interface CustomerDao extends DeleteDao<Long>, GetAndSaveDao<Customer, Long> {

}
