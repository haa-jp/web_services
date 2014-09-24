package com.avetti.nw.soapservices.customer;

import java.util.Date;

public class Order {
	private String orderNumber;
	private String generation;
	private String poNumber;
	private Date orderDate;
	private Double orderValue;
	private Double invoiceAmount;
	private Integer histSequence;
	private String orderType;
	private String status;
	private Date estShipDate;
	private Date shipDate;
	private Date invoiceDate;

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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getHistSequence() {
		return histSequence;
	}

	public void setHistSequence(Integer histSequence) {
		this.histSequence = histSequence;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEstShipDate() {
		return estShipDate;
	}

	public void setEstShipDate(Date estShipDate) {
		this.estShipDate = estShipDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
}
