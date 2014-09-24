package com.avetti.nw.store.invoices.controllers;

import com.avetti.nw.soapservices.invoices.OpenInvoiceResponse;


public class OpenInvoiceDTO {

	private int company;
	private String customerNumber;
	private OpenInvoiceResponse invoice;
	private String invoiceNumber;

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

	public OpenInvoiceResponse getInvoice() {
		return invoice;
	}

	public void setInvoice(OpenInvoiceResponse invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
