package com.batch.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.model.Customer;

@Repository("postgrescustomerRepository")
public interface CustomerRepository extends JpaRepository<Customer, UUID>{

}
