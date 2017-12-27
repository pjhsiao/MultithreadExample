package com.jeff.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class AcquireCurrency  {
	private final static String DetectCurrencyTag = "hidden-phone print_show";
	private final static String DetectURL = "http://rate.bot.com.tw/xrt?Lang=zh-TW";
	public static List<String> getCurrencies() throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(DetectURL);
		List<String> currencyCollector = new ArrayList<String>();
		try {
			CloseableHttpResponse resp = httpclient.execute(httpGet);
		    if(HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()){
		    	HttpEntity entity = resp.getEntity();
		    	if(null != entity){
		    		boolean isCurrency = false;
		    		for(String line: IOUtils.readLines(entity.getContent())){
		    			if(isCurrency){
		    				currencyCollector.add(line.trim());
		    				isCurrency = false;
		    			}
		    			
		    			if(-1 < line.indexOf(DetectCurrencyTag)){
		    				isCurrency = true;
		    			}
		    		}
		    	}
		    }
		    resp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.close();
		return currencyCollector;
	}
	public static Set<String> formatCurrencies(List<String> list){
		Set<String> currenciesFormated  = new HashSet<String>(list.size());
		for (int i = 0; i < list.size(); i++) {
			String currency = list.get(i);
			currency = currency.substring(currency.indexOf("(")+1, currency.length()-1);
			currenciesFormated.add(currency);
		}
		return currenciesFormated;
	}
}
