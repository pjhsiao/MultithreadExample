package com.jeff.main;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 *  test command-line to execute particular Test-Class,please following below.    
 *  gradle test --tests "com.jeff.main.AcquireCurrencyTest"
 * @author hsiaopojen
 *
 */
public class AcquireCurrencyTest {
	private AcquireCurrency acquireCurrency;
    private static int CURRENCY_COUNT = 19;
	@Before
    public void setUp() {
		acquireCurrency = new AcquireCurrency();
    }
	   
	@Test
	public void test() {
		try {
			assertEquals(CURRENCY_COUNT,acquireCurrency.getCurrencies().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
