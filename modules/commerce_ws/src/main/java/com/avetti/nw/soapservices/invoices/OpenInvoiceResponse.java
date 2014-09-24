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

import java.util.List;

/**
 * @author T.Kluchko
 *
 */
public class OpenInvoiceResponse {
	private InvoiceHeader invoiceHeader;
	private List<InvoiceDetail> invoiceDetails;
	
	public InvoiceHeader getInvoiceHeader() {
		return invoiceHeader;
	}
	public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}
	public List<InvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
}
