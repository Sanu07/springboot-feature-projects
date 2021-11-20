package com.consumer.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.consumer.dao.PaymentDao;
import com.consumer.exceptions.NotFoundException;
import com.consumer.model.Payment;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	List<Payment> payments;

	public PaymentDaoImpl() {
		this.payments = new ArrayList<>();
	}

	@Override
	public List<Payment> findAll() {
		return Objects.nonNull(payments) ? this.payments : Collections.emptyList();
	}

	@Override
	public Payment save(Payment payment) {
		if (Objects.isNull(payment.getId()) || payment.getId() > this.getSize()) {
			payment.setId(this.getSize() + 1L);
			this.payments.add(payment);
		} else {
			this.payments.set(payment.getId().intValue(), payment);
		}
		return payment;
	}

	@Override
	public Payment findById(Long identifier) {
		Optional<Payment> payment = this.payments.stream().filter(paymnt -> paymnt.getId().equals(identifier))
				.findAny();
		if (payment.isPresent()) {
			return payment.get();
		}
		throw new NotFoundException("No Payment with id " + identifier + " is found");
	}

	@Override
	public int getSize() {
		return this.payments.size();
	}
}
