package com.calculator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "currency_calculator")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String fromCurrency;
	private String toCurrency;
	private String convertedValue;
	private int port;
}
