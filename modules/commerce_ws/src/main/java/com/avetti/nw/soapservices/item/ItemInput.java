package com.avetti.nw.soapservices.item;


public class ItemInput {
	private Integer company;
	private String customerNumber;
	private String itemNumber;

	public ItemInput(Integer company, String customerNumber, String itemNumber) {
		super();
		this.company = company;
		this.customerNumber = customerNumber;
		this.itemNumber = itemNumber;
	}

	public ItemInput() {
		super();
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
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
}
