package com.avetti.nw.soapservices.invoices;

import java.util.Date;


public class InvoiceHeader {

	private String invoiceNumber;
	private Date invoiceDate;
	private Date ageDate;
	private Double invoiceAmount;
	private Double invoiceBalance;
	private Integer histSequence;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getAgeDate() {
		return ageDate;
	}
	public void setAgeDate(Date ageDate) {
		this.ageDate = ageDate;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Double getInvoiceBalance() {
		return invoiceBalance;
	}
	public void setInvoiceBalance(Double invoiceBalance) {
		this.invoiceBalance = invoiceBalance;
	}
	public Integer getHistSequence() {
		return histSequence;
	}
	public void setHistSequence(Integer histSequence) {
		this.histSequence = histSequence;
	}
}
