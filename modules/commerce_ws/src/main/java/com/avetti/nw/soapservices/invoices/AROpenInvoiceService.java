package com.avetti.nw.soapservices.invoices;


public interface AROpenInvoiceService {

	public OpenInvoicesResponse GetCustomerOpenInvoices(OpenInvoicesInput input);
	
	
	public OpenInvoiceResponse GetCustomerOpenInvoice(OpenInvoiceInput input);
}
