package com.avetti.nw.soapservices.invoices;


public interface ARPaidInvoiceService {
	
	public PaidInvoicesResponse GetCustomerPaidInvoices(PaidInvoicesInput input);
	
	public PaidInvoiceResponse GetCustomerPaidInvoice(PaidInvoiceInput input);

}
