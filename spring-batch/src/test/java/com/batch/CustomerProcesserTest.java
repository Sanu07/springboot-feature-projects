package com.batch;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.batch.processors.CustomerProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
public class CustomerProcesserTest {

	CustomerProcessor customerProcessor = new CustomerProcessor();
	
	@Test
	void test_fake_phoneNo() {
		String customerId = customerProcessor.getFakePhoneNo();
		log.info(customerId);
		assertEquals(customerId.length(), 10);
		assertTrue(customerId.matches("[0-9]+"));
	}
}
