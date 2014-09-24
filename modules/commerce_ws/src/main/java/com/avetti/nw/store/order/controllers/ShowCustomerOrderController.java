package com.avetti.nw.store.order.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.soapservices.order.AROrderService;
import com.avetti.nw.soapservices.order.Item;
import com.avetti.nw.soapservices.order.OrderInput;
import com.avetti.nw.soapservices.order.OrderOutput;
import com.avetti.simplemerce.common.controllers.ComponentController;


public class ShowCustomerOrderController extends ComponentController {
	private static final String HIST_SEQUENCE_PARAM = "hseq";
	private static final String GENERATION_PARAM = "gen";
	private static final String ORDER_NUMBER_PARAM = "ornum";
	
	private AROrderService service;
	public void setService(AROrderService service) {
		this.service = service;
	}

	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		CustomerOrderDTO command = (CustomerOrderDTO) 
				super.formBackingObject(request);

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
		return command;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView showForm(HttpServletRequest request, 
			HttpServletResponse response, BindException errors)
			throws Exception {
		ModelAndView mv = super.showForm(request, response, errors); 
		CustomerOrderDTO command = (CustomerOrderDTO) mv.getModel().get(
                getCommandName());

		OrderInput orderInput = new OrderInput();
		if(StringUtils.hasLength(command.getOrderNumber()))
			orderInput.setOrderNumber(command.getOrderNumber());
		if(StringUtils.hasLength(command.getGeneration()))
			orderInput.setGeneration(command.getGeneration());
		if(command.getHistSequence()!=null)
			orderInput.setHistSequence(command.getHistSequence());
		orderInput.setCompany(command.getCompany());
		OrderOutput serviceResponse = service.GetOrder(orderInput);
		command.setOrderOutput(serviceResponse);
		// genarate copy order link 
		StringBuilder copyOrderUrl=new StringBuilder();
		if(serviceResponse!=null){
			copyOrderUrl.append("action.html?mode=addItems&vid=")
				.append(getVendorId(request)).append("&url=basket.html?vid=")
				.append(getVendorId(request));
			
			int num=1;
			for (Item item : serviceResponse.getDetail()) 
				if(item!=null){
					copyOrderUrl.append("&ic").append(num).append("=")
						.append(item.getItemNumber().replace('.', '-'))
						.append("&qty").append(num).append("=")
						.append(item.getOrderQty());
					num++;
				}
		}
		command.setCopyOrderUrl(copyOrderUrl.toString());
		mv.getModel().put(getCommandName(), command);
		return mv;
	}
}
