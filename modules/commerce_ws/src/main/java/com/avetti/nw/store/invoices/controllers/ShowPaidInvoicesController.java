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
import com.avetti.nw.soapservices.invoices.ARPaidInvoiceService;
import com.avetti.nw.soapservices.invoices.PaidInvoicesInput;
import com.avetti.nw.soapservices.invoices.PaidInvoicesResponse;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;


public class ShowPaidInvoicesController extends ComponentController {

	private static final int DEFAULT_PAGESIZE = 20;
	private static final String PO_NUMBER = "poNumber";
	private static final String ORDER_NUMBER = "orderNumber";
	private static final String END_DATE = "endDate";
	private static final String PAGE = "page";
	private static final String INVOICE_NUMBER = "invoiceNumber";
	private static final String NUMRESULT = "numresult";
	
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
		PaidInvoicesDTO command = (PaidInvoicesDTO)super.formBackingObject(request);
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

		PaidInvoicesDTO command = (PaidInvoicesDTO) mv.getModel().get(
                getCommandName());

		PaidInvoicesInput paidInvoicesInput = new PaidInvoicesInput();

		if(StringUtils.hasLength(command.getInvoiceNumber()))
			paidInvoicesInput.setInvoiceNumber(command.getInvoiceNumber());
		if(StringUtils.hasLength(command.getOrderNumber()))
			paidInvoicesInput.setOrderNumber(command.getOrderNumber());
		if(StringUtils.hasLength(command.getPoNumber()))
			paidInvoicesInput.setPoNumber(command.getPoNumber());
		if(StringUtils.hasLength(command.getEndDate()))
			paidInvoicesInput.setEndDate(command.getEndDate());
		if(command.getPage() != null)
			paidInvoicesInput.setPage(command.getPage());
		if(command.getLimit()!=null)
			paidInvoicesInput.setLimit(command.getLimit());
		else 
			paidInvoicesInput.setLimit(DEFAULT_PAGESIZE);

		paidInvoicesInput.setCompany(command.getCompany());
		paidInvoicesInput.setCustomerNumber(command.getCustomerNumber());

		PaidInvoicesResponse serviceResponse = service.GetCustomerPaidInvoices(
				paidInvoicesInput);

		List<String> pageList = new LinkedList<String>();
		int pageCount = serviceResponse.getInvoiceCount() /
				paidInvoicesInput.getLimit();
		if((serviceResponse.getInvoiceCount() % paidInvoicesInput.getLimit())>0)
			pageCount++;
		for (int i = 0; i < pageCount; i++) {
			pageList.add(String.valueOf(i+1));
		}
		command.setPageList(pageList);
		command.setPaidInvoices(serviceResponse);

		mv.getModel().put(getCommandName(), command);
		return mv;
	}
}
