package com.avetti.nw.store.invoices.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.GlobalSettingsResolver;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.invoices.ARPaidInvoiceService;
import com.avetti.nw.soapservices.invoices.PaidInvoiceInput;
import com.avetti.nw.soapservices.invoices.PaidInvoiceResponse;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;


public class ShowPaidInvoiceController extends ComponentController {
	private static final String INV = "inv";
	private ARPaidInvoiceService service = null;
	private CustomerNumberResolver customerNumberResolver;

	public void setService(ARPaidInvoiceService service) {
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

		PaidInvoiceDTO command = (PaidInvoiceDTO) super.formBackingObject(request);

		command.setCompany(GlobalSettingsResolver.getCompany());;
		command.setCustomerNumber(customerNumberResolver.getCustomerNumber(
				getVendorId(request), sessionCustomer.getClientId(),
				new PriceParamsDTO()));
		
		if(WebUtils.hasSubmitParameter(request, INV))
			command.setInvoiceNumber(request.getParameter(INV));
		
		return command;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 

		PaidInvoiceDTO command = (PaidInvoiceDTO) mv.getModel().get(
                getCommandName());

		PaidInvoiceInput paidInvoiceInput = new PaidInvoiceInput();
		
		if(StringUtils.hasLength(command.getInvoiceNumber()))
			paidInvoiceInput.setInvoiceNumber(command.getInvoiceNumber());
		
		
		paidInvoiceInput.setCompany(command.getCompany());
		paidInvoiceInput.setCustomerNumber(command.getCustomerNumber());

		PaidInvoiceResponse serviceResponse = service.GetCustomerPaidInvoice(
				paidInvoiceInput);

		command.setInvoice(serviceResponse);
		mv.getModel().put(getCommandName(), command);
		return mv;
	}
}
