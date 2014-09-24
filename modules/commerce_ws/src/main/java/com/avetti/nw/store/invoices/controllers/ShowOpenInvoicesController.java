package com.avetti.nw.store.invoices.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.GlobalSettingsResolver;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.invoices.AROpenInvoiceService;
import com.avetti.nw.soapservices.invoices.OpenInvoicesInput;
import com.avetti.nw.soapservices.invoices.OpenInvoicesResponse;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;


public class ShowOpenInvoicesController extends ComponentController {

	private static final int DEFAULT_PAGESIZE = 20;
	private static final String PO_NUMBER = "poNumber";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String END_DATE = "endDate";
	private static final String PAGE = "page";
	private static final String INVOICE_NUMBER = "invoiceNumber";
	private static final String NUMRESULT = "numresult";
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

		OpenInvoicesDTO command = (OpenInvoicesDTO)super.formBackingObject(request);
		command.setCompany(GlobalSettingsResolver.getCompany());;
		command.setCustomerNumber(customerNumberResolver.getCustomerNumber(
				getVendorId(request), sessionCustomer.getClientId(), new PriceParamsDTO()));
		
		if(WebUtils.hasSubmitParameter(request, INVOICE_NUMBER))
			command.setInvoiceNumber(request.getParameter(INVOICE_NUMBER));
		if(WebUtils.hasSubmitParameter(request, ORDER_NUMBER))
			command.setOrderNumber(request.getParameter(ORDER_NUMBER));
		if(WebUtils.hasSubmitParameter(request, PO_NUMBER))
			command.setPoNumber(request.getParameter(PO_NUMBER));
		if(WebUtils.hasSubmitParameter(request, END_DATE))
			command.setEndDate(request.getParameter(END_DATE));
		if(WebUtils.hasSubmitParameter(request, PAGE))
			try {
				command.setPage(Integer.parseInt(request.getParameter(PAGE)));
			} catch (Exception e) {
			}
		if(WebUtils.hasSubmitParameter(request, NUMRESULT))
			try {
				command.setLimit(Integer.parseInt(request.getParameter(NUMRESULT)));
			} catch (Exception e) {
			}
		return command;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 

		OpenInvoicesDTO command = (OpenInvoicesDTO) mv.getModel().get(
                getCommandName());

		OpenInvoicesInput openInvoicesInput = new OpenInvoicesInput();

		if(StringUtils.hasLength(command.getInvoiceNumber()))
			openInvoicesInput.setInvoiceNumber(command.getInvoiceNumber());
		if(StringUtils.hasLength(command.getOrderNumber()))
			openInvoicesInput.setOrderNumber(command.getOrderNumber());
		if(StringUtils.hasLength(command.getPoNumber()))
			openInvoicesInput.setPoNumber(command.getPoNumber());
		if(StringUtils.hasLength(command.getEndDate()))
			openInvoicesInput.setEndDate(command.getEndDate());
		if(command.getPage() != null)
			openInvoicesInput.setPage(command.getPage());
		if(command.getLimit()!=null)
			openInvoicesInput.setLimit(command.getLimit());
		else 
			openInvoicesInput.setLimit(DEFAULT_PAGESIZE);

		openInvoicesInput.setCompany(command.getCompany());
		openInvoicesInput.setCustomerNumber(command.getCustomerNumber());

		OpenInvoicesResponse serviceResponse = service.GetCustomerOpenInvoices(
				openInvoicesInput);
		
		List<String> pageList = new LinkedList<String>();
		int pageCount = serviceResponse.getInvoiceCount() /
				openInvoicesInput.getLimit();
		if((serviceResponse.getInvoiceCount() % openInvoicesInput.getLimit())>0)
			pageCount++;
		for (int i = 0; i < pageCount; i++) {
			pageList.add(String.valueOf(i+1));
		}
		command.setPageList(pageList);
		command.setInvoiceCount(serviceResponse.getInvoiceCount());
		command.setInvoices(serviceResponse.getInvoices());

		mv.getModel().put(getCommandName(), command);
		return mv;
	}

}
