package com.avetti.nw.store.order.controllers;

import com.avetti.nw.soapservices.order.OrderOutput;


public class CustomerOrderDTO {
	
	private Integer company;
	private String customerNumber;
	private String orderNumber;
	private String generation;
	private Integer histSequence;
	private String copyOrderUrl;
	private OrderOutput orderOutput;

	
	public Integer getCompany() {
		return company;
	}
	public void setCompany(Integer company) {
		this.company = company;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
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
	public Integer getHistSequence() {
		return histSequence;
	}
	public void setHistSequence(Integer histSequence) {
		this.histSequence = histSequence;
	}
	public OrderOutput getOrderOutput() {
		return orderOutput;
	}
	public void setOrderOutput(OrderOutput orderOutput) {
		this.orderOutput = orderOutput;
	}
	public String getCopyOrderUrl() {
		return copyOrderUrl;
	}
	public void setCopyOrderUrl(String copyOrderUrl) {
		this.copyOrderUrl = copyOrderUrl;
	}
}
