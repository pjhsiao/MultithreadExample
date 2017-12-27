package com.jeff.threadObjects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jeff.domain.CurrencyRate;

public class HandleCurrencyRateRunnable implements Runnable {
	public static final String FileDEST = "tempFolder/";
	public static final String CurrencyRateURL = "http://rate.bot.com.tw/xrt/quote/day/";
	public static final String FileSecondaryName = ".pdf";
	private List<CurrencyRate> currencyRates = new ArrayList<CurrencyRate>();
	public static final int TableColumns = 6;
	public static final int[] TableWeightDistance= new int[]{2,1,1,1,1,1};
	private String currencyTypeName;
    private String eachFilePath;
	public HandleCurrencyRateRunnable(String currencyTypeName) {
		System.out.println("begin threading for table name:"+currencyTypeName);
		this.currencyTypeName = currencyTypeName;
		this.eachFilePath = FileDEST.concat(currencyTypeName).concat(FileSecondaryName);
	}

	@Override
	public void run() {
		File file = new File(FileDEST.concat(currencyTypeName).concat(FileSecondaryName));
		Document document = new Document();
		StringBuffer sb;
		try {
			System.out.println("create document name:"+currencyTypeName);
			sb = this.getCurrencyRateData(currencyTypeName);
			this.getFormatedHtmlToList(sb);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			PdfPTable table = new PdfPTable(TableColumns);
			table.setWidths(TableWeightDistance);
			for(CurrencyRate currencyRate: currencyRates){
				table.addCell(currencyRate.getRecordDateTime());
				table.addCell(currencyRate.getCurrencyType());
				table.addCell(currencyRate.getCashRateImport());
				table.addCell(currencyRate.getCashRateExport());
				table.addCell(currencyRate.getImmediateRateImport());
				table.addCell(currencyRate.getImmediateRateExport());
			}
			document.add(table);
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		} finally{
			document.close();
			System.out.println("finished document name:"+currencyTypeName);
		}
	}
	
	private StringBuffer getCurrencyRateData(String currencyTypeName) throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(CurrencyRateURL.concat(currencyTypeName));
		StringBuffer sb = new StringBuffer();
		try {
			CloseableHttpResponse resp = httpclient.execute(httpGet);
		    if(HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()){
		    	HttpEntity entity = resp.getEntity();
		    	if(null != entity){
		    		boolean isValidData = false;
		    		for(String line: IOUtils.readLines(entity.getContent())){
		    			if(isValidData){
		    				sb.append(line);
		    			}
		    			if(-1 < line.indexOf("<tbody>")){
		    				isValidData = true;
		    			}
		    			if(-1 < line.indexOf("</tbody>")){
		    				isValidData = false;
		    			}
		    		}
		    	}
		    }
		    resp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpclient.close();
		return sb;
	}
	//format html to get value of records
	private void getFormatedHtmlToList(StringBuffer sb){
		String[] trs = StringUtils.substringsBetween(sb.toString(), "<tr>", "</tr>");
		// parse html and insert into currencyRates
		for (String tr : trs) {
			String[] tds =  StringUtils.substringsBetween(tr.toString(), "\">", "</td>");
			CurrencyRate rate = new CurrencyRate();
			rate.setRecordDateTime(tds[0]);//時間
			rate.setCurrencyType(tds[1]);//幣別
			rate.setCashRateImport(tds[2]);//現金匯率本行買入
			rate.setCashRateExport(tds[3]);//現金匯率本行賣出
			rate.setImmediateRateImport(tds[4]);//即期匯率本行買入
		    rate.setImmediateRateExport(tds[5]);//即期匯率本行賣出
		    this.currencyRates.add(rate);
		}
	}
}
