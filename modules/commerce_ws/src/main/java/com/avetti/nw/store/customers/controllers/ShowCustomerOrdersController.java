package com.avetti.nw.store.customers.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.GlobalSettingsResolver;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.customer.ARCustomerService;
import com.avetti.nw.soapservices.customer.CustomerOrdersInput;
import com.avetti.nw.soapservices.customer.CustomerOrdersResponse;
import com.avetti.nw.soapservices.order.AROrderService;
import com.avetti.nw.soapservices.order.Item;
import com.avetti.nw.soapservices.order.OrderInput;
import com.avetti.nw.soapservices.order.OrderOutput;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ComponentController;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;


public class ShowCustomerOrdersController extends ComponentController {
	
	private static final int DEFAULT_PAGESIZE = 20;
	private static final String END_DATE = "endDate";
	private static final String ORDER_TYPE = "orderType";
	private static final String PAGE = "page";
	private static final String NUMRESULT = "numresult";
	private static final String HIST_SEQUENCE_PARAM = "hseq";
	private static final String GENERATION_PARAM = "gen";
	private static final String ORDER_NUMBER_PARAM = "ornum";
	
	private ARCustomerService service;
	
	private AROrderService orderService;
	public void setOrderService(AROrderService orderService) {
		this.orderService = orderService;
	}
	
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

		CustomerOrdersDTO command = (CustomerOrdersDTO) 
				super.formBackingObject(request);
		if(WebUtils.hasSubmitParameter(request, ORDER_TYPE))
			command.setOrderType(request.getParameter(ORDER_TYPE));
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
		if(WebUtils.hasSubmitParameter(request, ORDER_NUMBER_PARAM))
			command.setOrderNumber(request.getParameter(ORDER_NUMBER_PARAM));
		if(WebUtils.hasSubmitParameter(request, GENERATION_PARAM))
			command.setGeneration(request.getParameter(GENERATION_PARAM));
		if(WebUtils.hasSubmitParameter(request, HIST_SEQUENCE_PARAM))
			try {
				command.setHistSequence(Integer.parseInt(
						request.getParameter(HIST_SEQUENCE_PARAM)));
			} catch (Exception e) {
			}
		command.setCompany(GlobalSettingsResolver.getCompany());;
		command.setCustomerNumber(customerNumberResolver.getCustomerNumber(
				getVendorId(request), sessionCustomer.getClientId(), new PriceParamsDTO()));
		return command;
	}
	
	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 

		CustomerOrdersDTO command = (CustomerOrdersDTO) mv.getModel().get(
                getCommandName());

		CustomerOrdersInput customerOrdersInput = new CustomerOrdersInput();

		if(StringUtils.hasLength(command.getOrderType()))
			customerOrdersInput.setOrderType(command.getOrderType());
		if(StringUtils.hasLength(command.getEndDate()))
			customerOrdersInput.setEndDate(command.getEndDate());
		if(command.getPage() != null)
			customerOrdersInput.setPage(command.getPage());
		if(command.getLimit()!=null)
			customerOrdersInput.setLimit(command.getLimit());
		else 
			customerOrdersInput.setLimit(DEFAULT_PAGESIZE);
		customerOrdersInput.setCompany(command.getCompany());
		customerOrdersInput.setCustomerNumber(command.getCustomerNumber());
		
		CustomerOrdersResponse serviceResponse = service.GetCustomerOrders(
				customerOrdersInput);

		List<String> pageList = new LinkedList<String>();
		int pageCount = serviceResponse.getOrderCount() /
				customerOrdersInput.getLimit();
		if((serviceResponse.getOrderCount() % customerOrdersInput.getLimit())>0)
			pageCount++;
		for (int i = 0; i < pageCount; i++) {
			pageList.add(String.valueOf(i+1));
		}
		command.setPageList(pageList);
		command.setCustomerOrders(serviceResponse);

		mv.getModel().put(getCommandName(), command);
		return mv;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		CustomerOrdersDTO customerOrdersDTO = (CustomerOrdersDTO) command;
		OrderInput orderInput = new OrderInput();
		if(StringUtils.hasLength(customerOrdersDTO.getOrderNumber()))
			orderInput.setOrderNumber(customerOrdersDTO.getOrderNumber());
		if(StringUtils.hasLength(customerOrdersDTO.getGeneration()))
			orderInput.setGeneration(customerOrdersDTO.getGeneration());
		if(customerOrdersDTO.getHistSequence()!=null)
			orderInput.setHistSequence(customerOrdersDTO.getHistSequence());
		orderInput.setCompany(customerOrdersDTO.getCompany());
		OrderOutput serviceResponse = orderService.GetOrder(orderInput);

		if(serviceResponse!=null){
			ModelAndView mav = new ModelAndView(new RedirectView("action.html"));
			mav.addObject("mode", "addItems");
			mav.addObject("vid", getVendorId(request));
			mav.addObject("url", "basket.html?vid="+getVendorId(request));
			int num=1;
			for (Item item : serviceResponse.getDetail()) 
				if(item!=null){
					mav.addObject("ic"+num, item.getItemNumber().replace('.', '-'));
					mav.addObject("qty"+num, item.getOrderQty());
					num++;
				}
			return mav;
		}
		return new ModelAndView(new RedirectView("nwrevieworders.html"));
	}
}
