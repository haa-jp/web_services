package com.avetti.nw.store.businessobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avetti.commerce.categories.CategoryBO;
import com.avetti.nw.customer.CustomerNumberResolver;
import com.avetti.nw.soapservices.item.ItemInput;
import com.avetti.nw.soapservices.item.ItemPriceAvail;
import com.avetti.nw.soapservices.item.ItemService;
import com.avetti.simplemerce.businessobjects.VendorBO;
import com.avetti.simplemerce.datatransfer.StorePriceDTO;
import com.avetti.commerce.domainmodel.Offerprices;
import com.avetti.simplemerce.exceptions.DAOException;
import com.avetti.simplemerce.store.businessobjects.StorePriceBO;
import com.avetti.simplemerce.store.dataaccess.hibernate.StorePriceDAO;
import com.avetti.simplemerce.store.datatransfer.PriceParamsDTO;
import com.avetti.simplemerce.store.domain.IItemPrice;

public class StorePriceBOws extends StorePriceBO {

	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(StorePriceBOws.class);

	/**
	 * DAO for this subsystem.
	 */
	private StorePriceDAO storePriceDAO;

	/**
	 * Category Busness Object.
	 */
	private CategoryBO categoryBO;
	/**
	 * @param categoryBO
	 *            the categoryBO to set
	 */

	private ItemService service;

	private CustomerNumberResolver customerNumberResolver;

	/**
	 * PageTile names for price injection
	 */
	private Map pricedPageTiles;

	private VendorBO vendorBO;

	/**
	 * Create List of items with prices by List with item ids and other
	 * parameters see below.
	 * 
	 * @param itemIds
	 *            - list of item ids.
	 * @param vendorId
	 *            - id of vendor.
	 * @param paramsDTO
	 *            - PriceParamsDTO with three parameters.
	 * @return List with IItemPrices objects.
	 */
	public List createItemsFillPricesBy(List itemIds, String vendorId, PriceParamsDTO paramsDTO) {
		List items = null;
		try {
			items = this.storePriceDAO.getItemsBy(itemIds);
			// getItemPricesOpt(items, paramsDTO, vendorId, 0, new Date());
			getItemPricesNew(items, paramsDTO, vendorId, new Date(), paramsDTO.getCustumerId());
		} catch (DAOException e) {
			logger.error("Error while get prices: " + e);
		}

		return items;
	}

	/**
	 * Filter the founded price with offerCode and shopper group id. find the
	 * lowest price within the same item composite code and highest priority.
	 * 
	 * @param prices
	 *            all selected prices
	 * @param offerCode
	 *            offer code from the customer session
	 * @param shopperGroupId
	 *            shopper group identifier from the customer session
	 * @param currentDate
	 *            current date
	 * @return list of the Offerprices entities
	 */
	protected List<Offerprices> findOfferpriceForEachItem(List<Offerprices> prices,
			String offerCode, Long shopperGroupId, Date currentDate,
			Long currencyId, long clientId, Map priceParams) {
		logger.warn("Offerprice coming in is:  "+ prices.size());
		List<Offerprices> result = new ArrayList<Offerprices>();

		String currentItemCode = "";
		Offerprices rulesGroup0Price = null;
		Offerprices rulesGroup1Price = null;
		Offerprices defaultPrice = null;
		if (prices != null && prices.size() != 0) {
			for (Offerprices price : prices) {

				// Checking for incorrect database values
				if (StringUtils.isNotBlank(price.getItemcode()) && currencyId != null) {
					// Checking for currency
					if (currencyId.equals(price.getCurrencyid())) {

						if (isNewItem(price.getItemcode().trim(), currentItemCode.trim())) {

							currentItemCode = price.getItemcode();
							// This is new item in iteration. We need assign one
							// of
							// the already calculated price to previous item.
							checkRulesPriority(result, currentItemCode, rulesGroup0Price,
									rulesGroup1Price, defaultPrice);

							// Clearing result price objects for new item.
							rulesGroup0Price = null;
							rulesGroup1Price = null;
							defaultPrice = null;
						}

						// Checking for default price. Note, we should have only
						// one default price per (currency && itemcode).
						boolean isDefaultPrice = isDefaultPrice(price);
						if (defaultPrice == null && isDefaultPrice) {
							defaultPrice = price;
						} else if (!isDefaultPrice) {
							// Checking for the rules group 0
							rulesGroup0Price = checkRulesGroupPrice(price, offerCode,
									shopperGroupId, currentDate,
									rulesGroup0Price, 0);

							// Checking for the rules group 1
							rulesGroup1Price = checkRulesGroupPrice(price, offerCode,
									shopperGroupId, currentDate,
									rulesGroup1Price, 1);
						}
					}
				} else {
					logger.warn(new StringBuffer("Incorrect offerprice entity:")
							.append("Offerprices ID:")
							.append(price.getId()).toString());
				}
			}
		}
		logger.warn("Offerprice result right now is: "+ result.size());
		
		// Checking for the last item in iteration
		checkRulesPriority(result, currentItemCode, rulesGroup0Price,
				rulesGroup1Price, defaultPrice);
		
		PriceParamsDTO priceParamsDTO = new PriceParamsDTO();
		priceParamsDTO.setCurrencyId(currencyId);
		priceParamsDTO.setCustumerId(clientId);
		priceParamsDTO.setParams(priceParams);
		priceParamsDTO.setShopperGroupId(shopperGroupId);

		ItemPriceAvail response;
		String custProp = customerNumberResolver.getCustomerNumber(
				defaultPrice.getVendorid(), new Long(clientId), priceParamsDTO);
		logger.info("offerprice custProp is: "+ custProp);
		if (!StringUtils.isEmpty(custProp)) {
			for (Iterator<Offerprices> iter = prices.iterator(); iter.hasNext();) {
				Offerprices price = iter.next();
				String iCode = price.getItemcode();

				response = getFromWebService(1, custProp, iCode, price.getVendorid());

				if (response == null || StringUtils.isEmpty(response.getItemNumber())) {
					logger.info("NO offerprice for item from webservice: "+ price.getItemid());
				} else {
					logger.info("Adding offerprice for item: "+ price.getItemid());
					result.add(price);
					price.setListprice(response.getRetailPrice());
				    price.setPrice_1(response.getContractPrice());
						// TODO: should we use next lines?
						// curprice.setDealerPrice(response.getDealerPrice());
						// curprice.setRetailPrice(response.getRetailPrice());
				}
			}
		}
		logger.warn("Offerprice result right now is: "+ result.size());
		return result;
	}
	
	/**
	 * Makes web service request parameters see below.
	 * 
	 * @param company
	 *            - company id
	 * @param customerNumber
	 *            - web service custumer (shopperGroupID)
	 * @param itemNumber
	 *            - Item number
	 * @return ItemPriceAvail object
	 */
	public ItemPriceAvail getFromWebService(int company, String customerNumber,
			String itemNumber, String vendorId) {
		ItemPriceAvail response;
		try {
			//TODO: should we use skin id ?
			//Long skinId = 0L;
			//Vendor vendor = vendorBO.getVendor(vendorId);
			//if (vendor != null && vendor.getPreferences() != null) {
			//	skinId = vendor.getPreferences().getDefaultskinid();
			//}
			ItemInput iitem = new ItemInput();
			iitem.setCompany(company);
			iitem.setCustomerNumber(customerNumber);
			iitem.setItemNumber(itemNumber);
			// iitem.setSkinID(skinId.toString());
			response = service.GetItemPriceAvail(iitem);
		} catch (Exception e) {
			logger.warn(" getFromWebService() exception: " + e.getMessage());
			return null;
		}
		return response;
	}

	public void setVendorBO(VendorBO vendorBO) {
		this.vendorBO = vendorBO;
	}

	/**
	 * Fill List of IItemPrice with prices from field of this object offerprices
	 * by composite codes priceParams, min priority and vendorId. So this method
	 * do not exec any sql query to db, besides get default currency query.
	 * 
	 * @param priceData
	 *            - List of IItemPrice objects.
	 * @param priceParams
	 *            - dto with three params (currencyId, shopperGroupId,
	 *            offerCode).
	 * @param vendorId
	 *            - Vendor's id.
	 * @param nowDate
	 *            - some date.
	 */
	public void getItemPricesNew(List<IItemPrice> priceDataList,
			PriceParamsDTO priceParams, String vendorId,
			Date nowDate, long clientId) {
		// start validation
		if (priceDataList == null || priceDataList.size() <= 0 
				|| StringUtils.isEmpty(vendorId))
			return;
		//if runs from solr - index from db
		if (priceParams.getCustumerId() == -1){
			super.getItemPricesNew(priceDataList, priceParams, vendorId, nowDate, clientId);
			return;
		}

		ItemPriceAvail response;
		long customerId = 0;
		if (clientId != 0) {
			customerId = clientId;
		} else if (priceParams != null) {
			customerId = priceParams.getCustumerId();
		}		
		String custProp = customerNumberResolver.getCustomerNumber(vendorId, customerId, priceParams);
		if (!StringUtils.isEmpty(custProp)) {
			for (Iterator<IItemPrice> iter = priceDataList.iterator(); iter.hasNext();) {
				IItemPrice price = iter.next();
				String iCode = price.getItemCode();

				response = getFromWebService(1, custProp, iCode, vendorId);

				if (response == null || StringUtils.isEmpty(response.getItemNumber())) {
					setNullPrice(price);
				} else {
					StorePriceDTO curprice = price.getItemPrice();
					if (curprice == null){
						curprice = new StorePriceDTO();
						price.setItemPrice(curprice);
					}					
					curprice.setItemCode(iCode);
					curprice.setVendorId(vendorId);
					curprice.setListPrice(response.getRetailPrice());
					curprice.setPrice(response.getContractPrice());
					curprice.setPrice1(response.getContractPrice());
					curprice.setDealerPrice(response.getDealerPrice());
					curprice.setRetailPrice(response.getRetailPrice());
				}
			}
		} else {
			for (Iterator<IItemPrice> iter = priceDataList.iterator(); iter.hasNext();) {
				IItemPrice price = iter.next();
				setNullPrice(price);
			}
		}

	}

	/**
	 * Fill pageTiles items with IItemPrice objects with price from Offerprices
	 * table.
	 * 
	 * @param items
	 *            - pageTiles items with IItemPrice objects in its.
	 * @param priceParams
	 *            - PriceParamsDTO with three parameters.
	 * @param vendorId
	 *            - vendor id.
	 * @param fieldIndex
	 *            - index of item locate in pageTiles objects.
	 * @param nowDate
	 *            - some date.
	 */
	public void getItemPricesOpt(List objectPriceDataList, PriceParamsDTO priceParams,
			String vendorId, int fieldIndex, Date nowDate, long clientId) {
		List<IItemPrice> list = new ArrayList<IItemPrice>();
		for (Iterator<?> i = objectPriceDataList.iterator(); i.hasNext();) {
			try {
				IItemPrice itemPrice = castItemsElement(i.next(), fieldIndex);
				list.add(itemPrice);
			} catch (Exception e) {

			}
		}
		getItemPricesNew(list, priceParams, vendorId, nowDate, clientId);
	}

	/**
	 * Get Offerprice record according to the list of the itemid
	 * 
	 * @param itemIds
	 * @param vendorId
	 * @param currencyId
	 * @param shopperGroupId
	 * @param offerCode
	 * @param date
	 * @param clientId
	 * @return
	 * @throws DAOException
	 */
	public List<Offerprices> getPricesByItemIds(List<Long> itemIds, String vendorId,
			Long currencyId, Long shopperGroupId, String offerCode, Date date,
			long clientId) throws DAOException {

		if (currencyId == null || currencyId <= 0 || itemIds == null || itemIds.size() == 0)
			return new ArrayList<Offerprices>();
		StringBuffer query = new StringBuffer("FROM Offerprices WHERE ");
		query.append("vendorid = '").append(vendorId).append("' ");
		query.append("AND itemid IN (");

		boolean firstElement = true;
		for (Long itemid : itemIds) {
			if (firstElement) {
				query.append(itemid);
				firstElement = false;
			} else
				query.append(", ").append(itemid);
		}

		query.append(") AND currencyid=").append(currencyId);
		query.append(" ORDER BY itemid, itemcode, priority");

		@SuppressWarnings("unchecked")
		List<Offerprices> founded = storePriceDAO.listAndClose(query.toString());

		return findOfferpriceForEachItem(founded, offerCode, shopperGroupId,
				date, currencyId, clientId, null);
	} 
	
	@Override
	public List<Offerprices> getPricesByItemIds(List<Long> itemIds, String vendorId,
			Long currencyId, Long shopperGroupId, String offerCode, Date date,
			long clientId, Map priceParams) throws DAOException {
		
		if (currencyId == null || currencyId <= 0 || itemIds == null || itemIds.size() == 0)
			return new ArrayList<Offerprices>();
		StringBuffer query = new StringBuffer("FROM Offerprices WHERE ");
		query.append("vendorid = '").append(vendorId).append("' ");
		query.append("AND itemid IN (");

		boolean firstElement = true;
		for (Long itemid : itemIds) {
			if (firstElement) {
				query.append(itemid);
				firstElement = false;
			} else
				query.append(", ").append(itemid);
		}

		query.append(") AND currencyid=").append(currencyId);
		query.append(" ORDER BY itemid, itemcode, priority");

		@SuppressWarnings("unchecked")
		List<Offerprices> founded = storePriceDAO.listAndClose(query.toString());

		return findOfferpriceForEachItem(founded, offerCode, shopperGroupId,
				date, currencyId, clientId, priceParams);
	}

	public StorePriceDAO getStorePriceDAO() {
		return storePriceDAO;
	}

	public CategoryBO getCategoryBO() {
		return categoryBO;
	}

	public Map getPricedPageTiles() {
		return pricedPageTiles;
	}

	public void setStorePriceDAO(StorePriceDAO storePriceDAO) {
		super.setStorePriceDAO(storePriceDAO);
		this.storePriceDAO = storePriceDAO;
	}

	public void setCategoryBO(CategoryBO categoryBO) {
		super.setCategoryBO(categoryBO);
		this.categoryBO = categoryBO;
	}

	public void setPricedPageTiles(Map pricedPageTiles) {
		super.setPricedPageTiles(pricedPageTiles);
		this.pricedPageTiles = pricedPageTiles;
	}

	public void setService(ItemService service) {
		this.service = service;
	}

	public void setCustomerNumberResolver(CustomerNumberResolver customerNumberResolver) {
		this.customerNumberResolver = customerNumberResolver;
	}

}
