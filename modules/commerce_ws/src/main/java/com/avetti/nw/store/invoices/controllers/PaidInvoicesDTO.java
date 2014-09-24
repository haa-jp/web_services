package com.avetti.nw.store.invoices.controllers;

import java.util.List;

import com.avetti.nw.soapservices.invoices.PaidInvoicesResponse;


public class PaidInvoicesDTO {

	private int company;
	private String customerNumber;
	private String endDate;
	private String invoiceNumber;
	private String orderNumber;
	private String poNumber;
	private Integer page;
	private Integer limit;
	private PaidInvoicesResponse paidInvoices;
	private List<String> pageList;

	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
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
	public PaidInvoicesResponse getPaidInvoices() {
		return paidInvoices;
	}
	public void setPaidInvoices(PaidInvoicesResponse paidInvoices) {
		this.paidInvoices = paidInvoices;
	}
	public List<String> getPageList() {
		return pageList;
	}
	public void setPageList(List<String> pageList) {
		this.pageList = pageList;
	}
}
