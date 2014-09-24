package com.avetti.nw.soapservices.customer;

public interface ARCustomerService {
	
	public CustomerStatement GetARStatement (CustomerInput customer);

	public CustomerOrdersResponse GetCustomerOrders(CustomerOrdersInput customer);
	
	public ARSummary GetARSummary(CustomerInput customer );
	
	

}
