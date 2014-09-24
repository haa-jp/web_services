package com.avetti.nw.soapservices.invoices;

import java.util.Date;


public class InvoiceDetail {

	private Date transDate;
	private String transType;
	private Double transAmount;
	private Double paymentAmount;
	private Double cashDiscountAmount;
	private String checkNumber;
	private String adjustmentNumber;
	private String orderNumber;
	private String generation;
	private String poNumber;
	
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Double getCashDiscountAmount() {
		return cashDiscountAmount;
	}
	public void setCashDiscountAmount(Double cashDiscountAmount) {
		this.cashDiscountAmount = cashDiscountAmount;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getAdjustmentNumber() {
		return adjustmentNumber;
	}
	public void setAdjustmentNumber(String adjustmentNumber) {
		this.adjustmentNumber = adjustmentNumber;
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
}
