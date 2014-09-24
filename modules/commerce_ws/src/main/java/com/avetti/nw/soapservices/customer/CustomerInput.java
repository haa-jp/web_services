package com.avetti.nw.soapservices.customer;

public class CustomerInput {

	private Integer company;
	private String customerNumber;

	public CustomerInput() {
		super();
	}

	public CustomerInput(Integer company, String customerNumber) {
		super();
		this.company = company;
		this.customerNumber = customerNumber;
	}

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
}
