/**
 * Avetti Commerce
 *
 * NOTICE OF LICENSE
 *
 * This source file is subject to the Avetti Module License (AML 1.0)
 * that is bundled with this package in the file AMLICENSE.txt.
 *
 *  Copyright(c)2013 Avetti.com Corporation. (http://www.avetticommerce.com)
 *  License:   Avetti Module License (AML 1.0)
 */
package com.avetti.nw.soapservices.invoices;

/**
 * @author T.Kluchko
 *
 */
public class OpenInvoicesInput {
	private Integer company;
	
	private String customerNumber;
	private String endDate;
	private String invoiceNumber;
	private String orderNumber;
	private String poNumber;
	private Integer page;
	private Integer limit;

	public OpenInvoicesInput() {
		super();
	}

	public OpenInvoicesInput(Integer company, String customerNumber,
			String endDate, String invoiceNumber, String orderNumber,
			String poNumber, Integer page, Integer limit) {
		super();
		this.company = company;
		this.customerNumber = customerNumber;
		this.endDate = endDate;
		this.invoiceNumber = invoiceNumber;
		this.orderNumber = orderNumber;
		this.poNumber = poNumber;
		this.page = page;
		this.limit = limit;
	}

	public OpenInvoicesInput(Integer company, String customerNumber) {
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
	

}
