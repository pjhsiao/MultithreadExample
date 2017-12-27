package com.jeff.main;

import java.util.List;
import java.util.Set;

import com.jeff.threadObjects.HandleCurrencyRateRunnable;

public class Executor {
	public static void main(String[] args) {
		List<String> currencies;
		Set<String>  currenciesFormated;
		try {
			currencies = AcquireCurrency.getCurrencies();
			currenciesFormated = AcquireCurrency.formatCurrencies(currencies);
			for(String currency : currenciesFormated){
			   new Thread(new HandleCurrencyRateRunnable(currency)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
