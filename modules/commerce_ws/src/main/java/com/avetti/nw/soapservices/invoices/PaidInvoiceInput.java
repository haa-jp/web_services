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
public class PaidInvoiceInput {

	private Integer company;
	private String customerNumber;
	private String invoiceNumber;

	public PaidInvoiceInput() {
		super();
	}

	public PaidInvoiceInput(Integer company, String customerNumber,
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
