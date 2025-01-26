package com.agency.exchange.service.discounts;

import com.agency.exchange.configuration.Properties;
import com.agency.exchange.model.CurrencyConversionInput;
import com.agency.exchange.service.discounts.model.DiscountResult;
import com.agency.exchange.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountStrategiesTest {

    @InjectMocks
    private FlatDiscountBasedOnAmount flatDiscountBasedOnAmount;

    @InjectMocks
    private PriorityCustomerDiscount priorityCustomerDiscount;

    @InjectMocks
    private StoreAffiliateDiscount storeAffiliateDiscount;

    @InjectMocks
    private StoreEmployeeDiscount storeEmployeeDiscount;

    @Mock
    private Properties properties;

    @Test
    void testApplyDiscount_flatDiscountBasedOnAmount() {
        CurrencyConversionInput currencyConversionInput = TestUtil.getCurrencyConversionInput();
        Mockito.when(properties.getDiscountCapping()).thenReturn(new BigDecimal("5"));
        Mockito.when(properties.getMinimumValueForDiscountEligible()).thenReturn(new BigDecimal("50"));
        DiscountResult discountResult = flatDiscountBasedOnAmount.applyDiscount(currencyConversionInput, BigDecimal.ONE, properties);
        assertNotNull(discountResult);
        assertEquals(new BigDecimal("95"), discountResult.getFinalAmount());
        assertEquals("FLAT_DISCOUNT_BASED_ON_AMOUNT", discountResult.getDiscountCategory());
    }

    @Test
    void testApplyDiscount_priorityCustomerDiscount() {
        CurrencyConversionInput currencyConversionInput = TestUtil.getCurrencyConversionInput();
        Mockito.when(properties.getPriorityCustomerDiscount()).thenReturn(new BigDecimal("0.3"));
        currencyConversionInput.setPriorityCustomer(true);
        currencyConversionInput.setAmount(new BigDecimal("50"));
        DiscountResult discountResult = priorityCustomerDiscount.applyDiscount(currencyConversionInput, BigDecimal.ONE, properties);
        assertNotNull(discountResult);
        assertEquals(new BigDecimal("35.0"), discountResult.getFinalAmount());
        assertEquals("PRIORITY_CUSTOMER_DISCOUNT", discountResult.getDiscountCategory());
    }

    @Test
    void testApplyDiscount_storeAffiliateDiscount() {
        CurrencyConversionInput currencyConversionInput = TestUtil.getCurrencyConversionInput();
        Mockito.when(properties.getStoreAffiliateDiscount()).thenReturn(new BigDecimal("0.1"));
        currencyConversionInput.setStoreAffiliate(true);
        currencyConversionInput.setAmount(new BigDecimal("50"));
        DiscountResult discountResult = storeAffiliateDiscount.applyDiscount(currencyConversionInput, BigDecimal.ONE, properties);
        assertNotNull(discountResult);
        assertEquals(new BigDecimal("45.0"), discountResult.getFinalAmount());
        assertEquals("STORE_AFFILIATE_DISCOUNT", discountResult.getDiscountCategory());
    }

    @Test
    void testApplyDiscount_storeEmployeeDiscount() {
        CurrencyConversionInput currencyConversionInput = TestUtil.getCurrencyConversionInput();
        Mockito.when(properties.getStoreEmployeeDiscount()).thenReturn(new BigDecimal("0.05"));
        currencyConversionInput.setStoreEmployee(true);
        currencyConversionInput.setAmount(new BigDecimal("50"));
        DiscountResult discountResult = storeEmployeeDiscount.applyDiscount(currencyConversionInput, BigDecimal.ONE, properties);
        assertNotNull(discountResult);
        assertEquals(new BigDecimal("47.50"), discountResult.getFinalAmount());
        assertEquals("STORE_EMPLOYEE_DISCOUNT", discountResult.getDiscountCategory());
    }
}