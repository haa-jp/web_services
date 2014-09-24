package com.avetti.nw.customer;

import java.util.Iterator;
import java.util.Map;

import com.avetti.commerce.customers.CustomerBO;
import com.avetti.simplemerce.ComponentParametersNameResolver;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.datatransfer.StoreSkinInterceptorDTO.StoreSkin;
import com.avetti.commerce.domainmodel.Customer;
import com.avetti.commerce.domainmodel.Customerprops;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;

/**
 * Helper class which contains method of reading nw customer number from
 * avetti customer domain model. 
 */
public final class CustomerNumberResolver {
	private final String DEFAULT_CUSTOMER_NUMBER_PROPNAME = "Customer Number";
	
	private final CustomerBO customerBO;
	private final String customerNumberPropname; 
	
	private Map<String, String> themeNumbers;

	public CustomerNumberResolver(CustomerBO customerBO) {
		super();
		this.customerBO = customerBO;
		this.customerNumberPropname = DEFAULT_CUSTOMER_NUMBER_PROPNAME;
	}
	
	public CustomerNumberResolver(CustomerBO customerBO,
			String customerNumberPropname) {
		super();
		this.customerBO = customerBO;
		this.customerNumberPropname = customerNumberPropname;
	}

	public final String getCustomerNumber(String vendorId, long customerId,
			PriceParamsDTO priceParams) {
		
		String customerNumber = null;
		
		Customer customer = customerBO.find(vendorId, customerId);
		if (customer != null) {
			Iterator<?> it = customer.getProperties().iterator();
			while (customerNumber == null && it.hasNext()) {
				Customerprops p = (Customerprops) it.next();
				if (customerNumberPropname.equalsIgnoreCase(p.getPropname())){
					customerNumber = p.getPropvalue();
					break;
				}
			}
		} else if ( priceParams != null && priceParams.getParams() != null){
			StoreSkin storeSkin = (StoreSkin) priceParams.getParams().get(
					SessionNameResolver.getCurrentSkin(vendorId));
			if (storeSkin != null && themeNumbers != null){
				customerNumber = themeNumbers.get(storeSkin.getSkinid().toString());
			}
		}
		
		return customerNumber;
	}

	public Map<String, String> getThemeNumbers() {
		return themeNumbers;
	}

	public void setThemeNumbers(Map<String, String> themeNumbers) {
		this.themeNumbers = themeNumbers;
	}
}
