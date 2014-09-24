package com.avetti.nw.soapservices.invoices;


public class OpenInvoiceInput {

	private Integer company;
	private String customerNumber;
	private String invoiceNumber;

	public OpenInvoiceInput() {
		super();
	}

	public OpenInvoiceInput(Integer company, String customerNumber,
			String invoiceNumber) {
		super();
		this.company = company;
		this.customerNumber = customerNumber;
		this.invoiceNumber = invoiceNumber;
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
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
}
