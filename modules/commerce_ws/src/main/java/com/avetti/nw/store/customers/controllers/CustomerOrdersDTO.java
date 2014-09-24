package com.avetti.nw.store.customers.controllers;

import java.util.List;

import com.avetti.nw.soapservices.customer.CustomerOrdersResponse;


public class CustomerOrdersDTO {
	private Integer company;
	private String customerNumber;
	private String endDate;
	private String orderType;
	private Integer page;
	private Integer limit;
	
	private String orderNumber;
	private String generation;
	private Integer histSequence;
	
	private CustomerOrdersResponse customerOrders;
	private List<String> pageList;
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

	public CustomerOrdersResponse getCustomerOrders() {
		return customerOrders;
	}

	public void setCustomerOrders(CustomerOrdersResponse customerOrders) {
		this.customerOrders = customerOrders;
	}

	public List<String> getPageList() {
		return pageList;
	}

	public void setPageList(List<String> pageList) {
		this.pageList = pageList;
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
	
	
}
