package com.avetti.nw.store.invoices.controllers;

import com.avetti.nw.soapservices.invoices.PaidInvoiceResponse;


public class PaidInvoiceDTO {
	private int company;
	private String customerNumber;
	private String invoiceNumber;
	private PaidInvoiceResponse invoice;
	
	
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
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public PaidInvoiceResponse getInvoice() {
		return invoice;
	}
	public void setInvoice(PaidInvoiceResponse invoice) {
		this.invoice = invoice;
	}
}
