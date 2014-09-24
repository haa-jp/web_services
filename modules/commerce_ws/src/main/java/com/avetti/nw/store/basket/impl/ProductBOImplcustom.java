package com.avetti.nw.store.basket.impl;

import java.util.Map;

import com.avetti.commerce.basket.ProductAction;
import com.avetti.commerce.basket.ProductBean;
import com.avetti.commerce.basket.impl.ProductBOImpl;
import com.avetti.commerce.basket.impl.ProductBeanImpl;
import com.avetti.simplemerce.RegExp;
import com.avetti.simplemerce.datatransfer.SessionAttributeDTO;
import com.avetti.simplemerce.datatransfer.StorePriceDTO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;
import com.avetti.simplemerce.store.domain.ItemPriceImpl;

public class ProductBOImplcustom extends ProductBOImpl {
	
	public boolean fillProductPrice(ProductAction action, ProductBean product, 
			String prefixCode, Long shopperGroupId, String offerPriceCode,
			Long currencyId, long clientId, Map customPriceParams) {
		if(product == null) return false;
		
		ProductBeanImpl productImpl = (ProductBeanImpl) product;
		productImpl.setShopperGroupId(shopperGroupId);
		productImpl.setOfferPricecode(offerPriceCode);
		productImpl.setCurrencyId(currencyId);
		com.avetti.simplemerce.ProductBean product2 = (com.avetti.simplemerce.ProductBean) product;
		
		if(!RegExp.isNotEmpty(product2.getCompCode()))
			product2.setCompCode(product2.createCompCode(""));
			
		if(!RegExp.isNotEmpty(product2.getCompCode()))
			product2.setCompCode(product2.getCode());
		
		ItemPriceImpl item = new ItemPriceImpl();
		item.setCompCode(product2.getCompCode());
		item.setQty(product2.getQty());
		item.setItemCode(product2.getItemCode());
		
		if(item != null && RegExp.isNotEmpty(product2.getVendor())) {
			PriceParamsDTO priceParams = new PriceParamsDTO();
			priceParams.setShopperGroupId(shopperGroupId);
			priceParams.setOfferCode(offerPriceCode);
			priceParams.setCurrencyId(currencyId);
			priceParams.setCustumerId(clientId);
			priceParams.setParams(customPriceParams);
		
			storePrice.getItemPrice(item, priceParams, product2.getVendor());
		}
		
	
		StorePriceDTO result = item.getItemPrice();
		
		if(product2.getAttributesCollection()!=null) {
			for (SessionAttributeDTO attribute : ((Map<String, SessionAttributeDTO>)
					product2.getAttributesCollection()).values()) {
				if(attribute.getAttype()==3 && attribute.getChPrice()>0 
						&& attribute.getTextValue()!=null) {
					Double priceIncrement = null;
					try {
						priceIncrement = Double.valueOf(attribute.getTextValue());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					if(priceIncrement!=null) {
						result.setPrice(result.getPrice() 
								+ Math.abs(priceIncrement.doubleValue()));
					}
				}
			}
		}
		
		if(item.getItemPrice()!=null){
			product2.setPrice(item.getItemPrice().getPrice());
			product2.setListprice(item.getItemPrice().getListPrice());
			product2.setBillamount(item.getItemPrice().getBillAmount().doubleValue());
			product2.setBillperiod(item.getItemPrice().getBillPeriod().intValue());
			product2.setTimestobill(item.getItemPrice().getTimesToBill().intValue());
			product2.setOrgprice(item.getItemPrice().getPrice());
		}
		return true;
	}

}
