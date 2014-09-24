package com.avetti.nw.store.basket.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.avetti.commerce.basket.DetailsBean;
import com.avetti.commerce.basket.ProductAction;
import com.avetti.commerce.basket.ProductBean;
import com.avetti.commerce.basket.impl.FillPriceAction;
import com.avetti.commerce.store.basket.components.FillProductPriceBA;
import com.avetti.simplemerce.ComponentParametersNameResolver;
import com.avetti.simplemerce.SessionNameResolver;
import com.avetti.simplemerce.UrlParameterNameResolver;
import com.avetti.commerce.admin.customers.web.customer.ComponentParametersHelper;
import com.avetti.simplemerce.common.datatransfer.ComponentDTO;
import com.avetti.commerce.admin.customers.web.customer.ShopperGroupParametersDTO;
import com.avetti.simplemerce.datatransfer.SessionCustomerDTO;

public class FillProductPriceBAcustom extends FillProductPriceBA {

	public void process(ComponentDTO parameters, Object command) {

		Vector<ProductBean> products = (Vector) parameters.getUserParams().get(
				ComponentParametersNameResolver.getProducts());

		/*
		 * Retrive the parameters related to price calculation
		 */
		String vid = parameters.getVendorSettings().getVendorId();

		String prefixCode = parameters.getRequestParameter(UrlParameterNameResolver.getPrefixCode());

		ShopperGroupParametersDTO shopperGroupParametersDTO = 
			ComponentParametersHelper.getShopperGroupParametersDTO(vid, parameters);
		Long shopperGroupId = manageAnonymousCustomerBA.getShopperGroupId(vid,
				shopperGroupParametersDTO);

		String offerPriceCode = (String) parameters.getSessionParams().get(
				SessionNameResolver.getOfferPriceCode() + "_" 
				+ parameters.getVendorSettings().getVendorId());

		Long currencyId = (Long) parameters.getSessionParams().get(
				SessionNameResolver.getStoreCurrency() + "_" 
				+ parameters.getVendorSettings().getVendorId());

		Vector<ProductBean> allProducts = new Vector<ProductBean>();
		addAllProduct(products, allProducts);
		if ( products == null || products.isEmpty()){
			try{
			products = ((DetailsBean) parameters.getSessionParams().get(
					SessionNameResolver.getProductsDetiles(vid))).getAllProducts();
			addAllProduct(products, allProducts);
			} catch(Exception e){
				//no products in basket
			}
		}
		// calculate the price
		String requestOfferPriceCode = parameters.getRequestParameter(
				SessionNameResolver.getOfferPriceCode() + "_"
				+ parameters.getVendorSettings().getVendorId());
		
		Map customPriceParams = new HashMap();
		customPriceParams.put(SessionNameResolver.getCurrentSkin(vid),
			parameters.getSessionParams().get(SessionNameResolver.getCurrentSkin(vid)));
		
		SessionCustomerDTO customer = (SessionCustomerDTO) parameters.getSessionParams()
			.get(SessionNameResolver.getCustomerDTOName(vid));
		long clientId = customer == null? 0 : customer.getClientId();
		for (ProductBean product : allProducts) {
			ProductAction action = product.popAction(FillPriceAction.class);

			if (StringUtils.isNotEmpty(requestOfferPriceCode)) {
				productBO.fillProductPrice(action, product, prefixCode,
						shopperGroupId, offerPriceCode, currencyId, clientId,
						customPriceParams);
			} else {
				while (action != null) {

					productBO.fillProductPrice(action, product, prefixCode,
							shopperGroupId, offerPriceCode, currencyId, clientId,
							customPriceParams);

					action = product.popAction(FillPriceAction.class);
				}
			}

		}
	}

}
