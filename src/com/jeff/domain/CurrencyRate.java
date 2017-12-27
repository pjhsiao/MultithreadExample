package com.jeff.domain;

public class CurrencyRate {
	private String recordDateTime;
	private String currencyType;
	private String cashRateImport;
	private String cashRateExport;
	private String immediateRateImport;
	private String immediateRateExport;
	
	public String getRecordDateTime() {
		return recordDateTime;
	}
	public void setRecordDateTime(String recordDateTime) {
		this.recordDateTime = recordDateTime;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getCashRateImport() {
		return cashRateImport;
	}
	public void setCashRateImport(String cashRateImport) {
		this.cashRateImport = cashRateImport;
	}
	public String getCashRateExport() {
		return cashRateExport;
	}
	public void setCashRateExport(String cashRateExport) {
		this.cashRateExport = cashRateExport;
	}
	public String getImmediateRateImport() {
		return immediateRateImport;
	}
	public void setImmediateRateImport(String immediateRateImport) {
		this.immediateRateImport = immediateRateImport;
	}
	public String getImmediateRateExport() {
		return immediateRateExport;
	}
	public void setImmediateRateExport(String immediateRateExport) {
		this.immediateRateExport = immediateRateExport;
	}
	@Override
	public String toString() {
		return "CurrencyRate [recordDateTime=" + recordDateTime + ", currencyType=" + currencyType + ", cashRateImport="
				+ cashRateImport + ", cashRateExport=" + cashRateExport + ", immediateRateImport=" + immediateRateImport
				+ ", immediateRateExport=" + immediateRateExport + "]";
	}
	
}
