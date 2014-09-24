package com.avetti.nw.store.checkout.businessaction;

import javax.servlet.http.HttpServletRequest;

import com.avetti.commerce.store.checkout.businessaction.OrderPropertiesBA;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.common.controllers.ServletUtils;
import com.avetti.simplemerce.datatransfer.MultiShippingDTO;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;
import com.avetti.commerce.domainmodel.Catalogskins;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;

public class OrderPropertiesBACustom extends OrderPropertiesBA {
	
	private CustomerNumberResolver customerNumberResolver;

	public CustomerNumberResolver getCustomerNumberResolver() {
		return customerNumberResolver;
	}

	public void setCustomerNumberResolver(CustomerNumberResolver customerNumberResolver) {
		this.customerNumberResolver = customerNumberResolver;
	}
	
	@Override
	protected void customMethod(HttpServletRequest request, MultiShippingDTO multiShippingDTO) {
		String vendorId = ServletUtils.getVendorId(request);
		SessionCustomerDTO customer = (SessionCustomerDTO) request.getSession()
			.getAttribute(SessionNameResolver.getCustomerDTOName(vendorId));
		Catalogskins skin = (Catalogskins) request.getSession().getAttribute(
				SessionNameResolver.getCurrentSkin(vendorId));
		
		PriceParamsDTO pDTO = new PriceParamsDTO();
		pDTO.getParams().put(SessionNameResolver.getCurrentSkin(vendorId), skin);
		
		multiShippingDTO.getOrderproperties().put("Customer Number",
			customerNumberResolver.getCustomerNumber(vendorId, customer.getClientId(), pDTO));
	}

}
