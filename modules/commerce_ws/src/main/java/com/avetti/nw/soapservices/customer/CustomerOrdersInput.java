package com.avetti.nw.soapservices.customer;


public class CustomerOrdersInput {

	private Integer company;
	private String customerNumber;
	private String endDate;
	private String orderType;
	private Integer page;
	private Integer limit;
	
	public CustomerOrdersInput() {
		super();
	}

	public CustomerOrdersInput(Integer company, String customerNumber) {
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
