package com.jeff.main;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jeff.threadObjects.HandleCurrencyRateRunnable;

public class Executor {
	
	public static void main(String[] args) {
		List<String> currencies;
		Set<String>  currenciesFormated;
		try {
			currencies = AcquireCurrency.getCurrencies();
			currenciesFormated = AcquireCurrency.formatCurrencies(currencies);
			for(String currency : currenciesFormated){
			   Thread thread = new Thread(new HandleCurrencyRateRunnable(currency));
			   thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
