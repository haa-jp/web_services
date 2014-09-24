package com.avetti.nw.soapservices.order;


public class OrderInput {
	private Integer company;
	private String orderNumber;
	private String generation;
	private Integer histSequence;

	public OrderInput() {
		super();
	}

	public OrderInput(Integer company, String orderNumber, String generation,
			Integer histSequence) {
		super();
		this.company = company;
		this.orderNumber = orderNumber;
		this.generation = generation;
		this.histSequence = histSequence;
	}

	public Integer getCompany() {
		return company;
	}
	public void setCompany(Integer company) {
		this.company = company;
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
