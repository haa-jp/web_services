package com.avetti.nw.soapservices.invoices; 

import java.util.Date;

public class Invoice {

	private String invoiceNumber;
	private Double financeCharge;
	private Date invoiceDate;
	private Date ageDate;
	private Double invoiceAmount;
	private Double invoiceBalance;
	private Date lastTransDate;
	private Integer daysOpen;
	private Integer histSequence;
	private String orderNumber;
	private String generation;
	private String poNumber;
	private String checkNumber;
	private Double paymentAmount;
	
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Double getFinanceCharge() {
		return financeCharge;
	}
	public void setFinanceCharge(Double financeCharge) {
		this.financeCharge = financeCharge;
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
	public Date getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	public Integer getDaysOpen() {
		return daysOpen;
	}
	public void setDaysOpen(Integer daysOpen) {
		this.daysOpen = daysOpen;
	}
	public Integer getHistSequence() {
		return histSequence;
	}
	public void setHistSequence(Integer histSequence) {
		this.histSequence = histSequence;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getGeneration() {
		return generation;
	}
	public void setGeneration(String generation) {
		this.generation = generation;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
}
