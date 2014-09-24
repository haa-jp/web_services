package com.avetti.nw.soapservices.invoices;

import java.util.List;


public class OpenInvoicesResponse {

	private Integer invoiceCount;
	private List<Invoice> invoices;

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
}
