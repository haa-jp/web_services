package com.avetti.nw.common.businessaction.components;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.avetti.commerce.component.ComponentSensor;
import com.avetti.commerce.customerpropdef.CustomerpropDEF;
import com.avetti.nw.soapservices.customer.ARCustomerService;
import com.avetti.nw.soapservices.customer.ARSummary;
import com.avetti.nw.soapservices.customer.CustomerInput;
import com.avetti.simplemerce.ModelNameResolver;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.businessaction.CustomerPropertyBA;
import com.avetti.simplemerce.common.businessaction.components.IComponentBA;
import com.avetti.simplemerce.common.datatransfer.AnonymousCustomerDTO;
import com.avetti.simplemerce.common.datatransfer.ComponentDTO;
import com.avetti.commerce.domainmodel.Customerprops;

public class ARCustomerComponentBA implements IComponentBA, ComponentSensor {

	private static final String CUST_NUM = "Customer Number";
	
	protected final Log logger = LogFactory.getLog(ARCustomerComponentBA.class);
	
	private final String description = new StringBuffer(
		"This component gets Customer AR information from NW service if customer"
		).append(" has Customer Number property.\n")
		.append(" Expected URL parameters: None\n\n")
		.append("Generated DTO : ARCustomerDTO\n\n")
		.append("Can be Used on Template(s): Any Template")
		.toString();
	
	private int priority;
	
	/**
	 * spring properties
	 */
	
	private ARCustomerService service;
	
	private CustomerPropertyBA custPropertyBA;
	
	/**
	 * End of spring properties
	 */
		
	@Override
	public void process(ComponentDTO parameters, Object command) {
		
		AnonymousCustomerDTO customer = (AnonymousCustomerDTO) parameters.getSessionParams()
			.get(SessionNameResolver.getAnonymousCustomerDTO());
		if (customer != null){
			Customerprops prop = custPropertyBA.getCustomerprop(
					customer.getCustomerId(), CUST_NUM);
			if (prop != null){
				try{
					String customerNumber = prop.getPropvalue();
					
					CustomerInput request = new CustomerInput(1, customerNumber);
					
					ARSummary response = service.GetARSummary(request);
					
					Map<String, String> result = new HashMap<String, String>();
					result.put("Address1", response.getAddress1());
					result.put("Address2", response.getAddress2());
					result.put("Address3", response.getAddress3());
					result.put("Address4", response.getAddress4());
					result.put("City", response.getCity());
					result.put("Customer Name", response.getCustomerName());
					result.put("State", response.getState());
					result.put("Terms Description", response.getTermsDescription());
					result.put("Zip Code", response.getZipCode());
					result.put("Age Days Period 1", String.valueOf(response.getAgeDaysPeriod1()));
					result.put("Age Days Period 2", String.valueOf(response.getAgeDaysPeriod2()));
					result.put("Age Days Period 3", String.valueOf(response.getAgeDaysPeriod3()));
					result.put("Age Days Period 4", String.valueOf(response.getAgeDaysPeriod4()));
					result.put("Age Days Period 1 Amt", String.valueOf(response.getAgePeriod1Amt()));
					result.put("Age Days Period 2 Amt", String.valueOf(response.getAgePeriod2Amt()));
					result.put("Age Days Period 3 Amt", String.valueOf(response.getAgePeriod3Amt()));
					result.put("Age Days Period 4 Amt", String.valueOf(response.getAgePeriod4Amt()));
					result.put("Amount Due", String.valueOf(response.getAmountDue()));
					result.put("Billing Period Amt", String.valueOf(response.getBillingPeriodAmt()));
					result.put("Future Amount", String.valueOf(response.getFutureAmount()));
					result.put("Open Order Amount", String.valueOf(response.getOpenOrderAmount()));
					result.put("Sales Last Year", String.valueOf(response.getSalesLastYear()));
					result.put("Sales Month To Date", String.valueOf(response.getSalesMonthToDate()));
					result.put("Sales Year To Date", String.valueOf(response.getSalesYearToDate()));
					result.put("Date Of First Sale", String.valueOf(response.getDateOfFirstSale()));
					result.put("Last Payment Date", String.valueOf(response.getLastPaymentDate()));
					result.put("State", String.valueOf(response.getState()));
										
					parameters.getModel().put( "ARCustomerDTO", result);
				} catch(Exception e){
					logger.info(" Can't get from webservice: " + e.getMessage());
				}
			}
		}
	}

	@Override
	public String getIdentity() {
		return "arCustomerComponent";
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setCustPropertyBA(CustomerPropertyBA custPropertyBA) {
		this.custPropertyBA = custPropertyBA;
	}

	public ARCustomerService getService() {
		return service;
	}

	public void setService(ARCustomerService service) {
		this.service = service;
	}

	
}
