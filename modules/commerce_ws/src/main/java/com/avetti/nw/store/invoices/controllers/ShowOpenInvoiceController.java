package com.avetti.nw.store.invoices.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.GlobalSettingsResolver;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.invoices.AROpenInvoiceService;
import com.avetti.nw.soapservices.invoices.OpenInvoiceInput;
import com.avetti.nw.soapservices.invoices.OpenInvoiceResponse;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;



public class ShowOpenInvoiceController extends ComponentController {
	private static final String INV = "inv";
	private AROpenInvoiceService service = null;
	private CustomerNumberResolver customerNumberResolver;

	public void setService(AROpenInvoiceService service) {
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
		OpenInvoiceDTO command = (OpenInvoiceDTO) super.formBackingObject(request);

		command.setCompany(GlobalSettingsResolver.getCompany());;
		command.setCustomerNumber(customerNumberResolver.getCustomerNumber(
				getVendorId(request), sessionCustomer.getClientId(), new PriceParamsDTO()));

		if(WebUtils.hasSubmitParameter(request, INV))
			command.setInvoiceNumber(request.getParameter(INV));
		
		return command;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 

		OpenInvoiceDTO command = (OpenInvoiceDTO) mv.getModel().get(
                getCommandName());

		OpenInvoiceInput openInvoiceInput = new OpenInvoiceInput();
		
		if(StringUtils.hasLength(command.getInvoiceNumber()))
			openInvoiceInput.setInvoiceNumber(command.getInvoiceNumber());
		
		
		openInvoiceInput.setCompany(command.getCompany());
		openInvoiceInput.setCustomerNumber(command.getCustomerNumber());

		OpenInvoiceResponse serviceResponse = service.GetCustomerOpenInvoice(
				openInvoiceInput);

		command.setInvoice(serviceResponse);
		mv.getModel().put(getCommandName(), command);
		return mv;
	}

}
