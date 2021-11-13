package com.consumer.dao.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.consumer.dao.PaymentDao;
import com.consumer.enums.PaymentMode;
import com.consumer.exceptions.NotFoundException;
import com.consumer.model.Payment;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	List<Payment> payments;

	public PaymentDaoImpl() {
		this.payments = new ArrayList<>();
		this.payments.add(Payment.builder().id(1L).amountPaid(BigInteger.valueOf(299)).dueAmount(BigInteger.ZERO)
				.mode(PaymentMode.ONLINE).paidAt(LocalDateTime.now()).build());
		this.payments.add(Payment.builder().id(2L).amountPaid(BigInteger.valueOf(399)).dueAmount(BigInteger.ZERO)
				.mode(PaymentMode.ONLINE).paidAt(LocalDateTime.now().plusMinutes(15)).build());
		this.payments.add(Payment.builder().id(3L).amountPaid(BigInteger.valueOf(499)).dueAmount(BigInteger.ZERO)
				.mode(PaymentMode.ONLINE).paidAt(LocalDateTime.now().plusHours(1)).build());
	}

	@Override
	public List<Payment> findAll() {
		return Objects.nonNull(payments) ? this.payments : Collections.emptyList();
	}

	@Override
	public Payment save(Payment payment) {
		if (Objects.isNull(payment.getId())) {
			throw new UnsupportedOperationException();
		}
		this.payments.add(payment);
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
