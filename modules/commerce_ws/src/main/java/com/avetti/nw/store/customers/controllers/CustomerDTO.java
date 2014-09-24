package com.avetti.nw.store.customers.controllers;

import com.avetti.nw.soapservices.customer.ARSummary;


public class CustomerDTO {
	
	private Integer company;
	
	private String customerNumber;

	private ARSummary arSummary;
	
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

	public ARSummary getArSummary() {
		return arSummary;
	}

	public void setArSummary(ARSummary arSummary) {
		this.arSummary = arSummary;
	}
}
