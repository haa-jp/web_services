package com.avetti.nw.store.customers.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.avetti.nw.GlobalSettingsResolver;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.customer.ARCustomerService;
import com.avetti.nw.soapservices.customer.ARSummary;
import com.avetti.nw.soapservices.customer.CustomerInput;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;


public class ShowCustomerController extends ComponentController {

	private ARCustomerService service;
	
	private CustomerNumberResolver customerNumberResolver;

	public void setService(ARCustomerService service) {
		this.service = service;
	}

	public void setCustomerNumberResolver(
			CustomerNumberResolver customerNumberResolver) {
		this.customerNumberResolver = customerNumberResolver;
	}


	protected Object formBackingObject(HttpServletRequest request) throws Exception{
		SessionCustomerDTO sessionCustomer = 
				(SessionCustomerDTO) request.getSession().getAttribute(
						SessionNameResolver.getCustomerDTOName(getVendorId(request)));

		CustomerDTO command = (CustomerDTO) super.formBackingObject(request);
		command.setCompany(GlobalSettingsResolver.getCompany());;
		command.setCustomerNumber(customerNumberResolver.getCustomerNumber(
				getVendorId(request), sessionCustomer.getClientId(),
				new PriceParamsDTO()));
		return command;
	}

	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 
		CustomerDTO command = (CustomerDTO) mv.getModel().get(getCommandName());

		CustomerInput customerInput = new CustomerInput();

		customerInput.setCompany(command.getCompany());
		customerInput.setCustomerNumber(command.getCustomerNumber());
		ARSummary arSummary = service.GetARSummary(customerInput);
		command.setArSummary(arSummary);

		return super.showForm(request, response, errors);
	}
}
