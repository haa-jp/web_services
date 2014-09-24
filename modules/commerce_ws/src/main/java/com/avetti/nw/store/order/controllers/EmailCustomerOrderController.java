package com.avetti.nw.store.order.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.avetti.nw.soapservices.order.AROrderService;
import com.avetti.nw.soapservices.order.OrderInput;
import com.avetti.nw.soapservices.order.OrderOutput;
import com.avetti.simplemerce.Glob;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.businessaction.IManageCustomerBA;
import com.avetti.simplemerce.common.controllers.ServletUtils;
import com.avetti.simplemerce.common.dataaccess.GeneratedEmailSettingsDTO;
import com.avetti.simplemerce.datatransfer.CustomerDTO;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.simplemerce.exceptions.BAException;
import com.avetti.simplemerce.exceptions.BOException;
import com.avetti.simplemerce.exceptions.DAOException;
import com.avetti.simplemerce.thread.EmailSenderThread;


public class EmailCustomerOrderController extends AbstractFormController {
	private static final String HIST_SEQUENCE_PARAM = "hseq";
	private static final String GENERATION_PARAM = "gen";
	private static final String ORDER_NUMBER_PARAM = "ornum";

	/*Spring properties*/
	private String urlParameterName = "url";
	private AROrderService service;
	private EmailSenderThread emailSenderThread;
	private String emailName = "nwordermail";
	private IManageCustomerBA manageCustomerBA;
	/*End Spring properties*/

	public void setUrlParameterName(String urlParameterName) {
		this.urlParameterName = urlParameterName;
	}

	public void setService(AROrderService service) {
		this.service = service;
	}

	public void setEmailSenderThread(EmailSenderThread emailSenderThread) {
		this.emailSenderThread = emailSenderThread;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public void setManageCustomerBA(IManageCustomerBA manageCustomerBA) {
		this.manageCustomerBA = manageCustomerBA;
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		return createReirectView(request);
	}

	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		return createReirectView(request);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String vendorId = ServletUtils.getVendorId(request);

		SessionCustomerDTO sessionCustomer = (SessionCustomerDTO) request.getSession()
				.getAttribute(SessionNameResolver.getCustomerDTOName(vendorId));

		// prepare object for email 
		CustomerOrderDTO command = new CustomerOrderDTO();
		if (WebUtils.hasSubmitParameter(request, ORDER_NUMBER_PARAM))
			command.setOrderNumber(request.getParameter(ORDER_NUMBER_PARAM));
		if (WebUtils.hasSubmitParameter(request, GENERATION_PARAM))
			command.setGeneration(request.getParameter(GENERATION_PARAM));
		if (WebUtils.hasSubmitParameter(request, HIST_SEQUENCE_PARAM))
			try {
				command.setHistSequence(Integer.parseInt(request
						.getParameter(HIST_SEQUENCE_PARAM)));
			} catch (Exception e) {
			}
		OrderInput orderInput = new OrderInput();
		if (StringUtils.hasLength(command.getOrderNumber()))
			orderInput.setOrderNumber(command.getOrderNumber());
		if (StringUtils.hasLength(command.getGeneration()))
			orderInput.setGeneration(command.getGeneration());
		if (command.getHistSequence() != null)
			orderInput.setHistSequence(command.getHistSequence());
		orderInput.setCompany(command.getCompany());
		OrderOutput serviceResponse = service.GetOrder(orderInput);
		command.setOrderOutput(serviceResponse);

		// send email
		GeneratedEmailSettingsDTO emailSetting = new GeneratedEmailSettingsDTO();
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("customerOrderDTO", command);
		emailSetting.setModelData(params);
		Collection<String> addresses = new ArrayList<String>();
		addresses.add(getCustomerEmail(sessionCustomer.getClientId()));
		emailSetting.setEmailAddress(addresses);
		emailSetting.setClientId(sessionCustomer.getClientId());
		emailSetting.setVendorId(vendorId);
		emailSetting.setEmailName(emailName);
		// set settings to queue
		emailSenderThread.addEmailSettings(emailSetting);
		return createReirectView(request);
	}
	
	private ModelAndView createReirectView(HttpServletRequest request) throws Exception{
		String redirectUrl = request.getParameter(urlParameterName);
		if (redirectUrl == null)
			throw new Exception("Redirect URL was not set!");
		if (Glob.DEBUG())
			logger.info("Redirect to the " + redirectUrl);
		return new ModelAndView(new RedirectView(redirectUrl));
	}
	
	/**
	 * get customer email by client id
	 * @param clientID
	 * @return customer eamil
	 */
	private String getCustomerEmail(long clientID){
		CustomerDTO customerDTO = new CustomerDTO();
		try {
			manageCustomerBA.getCustomerById(clientID,customerDTO);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BAException e) {
			e.printStackTrace();
		} catch (BOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return customerDTO.getEmail();
	}

}
