package com.test.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.entity.Status;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ParamConstructorTest {

	@InjectMocks
	ParamConstructor paramConstructor;
	
	@BeforeAll
	void setUp() {
		paramConstructor = new ParamConstructor(Status.builder().field("F1").value("V1").build(), "S1");
	}
	
	@Test
	void testIsValid() {
		assertTrue(paramConstructor.isValid("F1", "V1", "S1"));
	}

}
