package com.avetti.nw.store.invoices.controllers;

import java.util.List;

import com.avetti.nw.soapservices.invoices.Invoice;

public class OpenInvoicesDTO {
	
	private int company;
	private List<String> pageList;
	private String customerNumber;
	private String endDate;
	private String invoiceNumber;
	private String orderNumber;
	private String poNumber;
	private Integer page;
	private Integer limit;
	private Integer invoiceCount;
	private List<Invoice> invoices;

	
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
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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
	public Integer getInvoiceCount() {
		return invoiceCount;
	}
	public void setInvoiceCount(Integer invoiceCount) {
		this.invoiceCount = invoiceCount;
	}
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	public List<String> getPageList() {
		return pageList;
	}
	public void setPageList(List<String> pageList) {
		this.pageList = pageList;
	}
}
