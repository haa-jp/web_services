package com.avetti.commerce.module.inventorybaws.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avetti.commerce.store.advancedsolrsearch.web.advancedsolrsearch.AdvancedSolrControllerCustomData;
import com.avetti.commerce.store.controllers.StoreControllerCustomData;
import com.avetti.simplemerce.pagetiles.datatransfer.PageTileDTO;
import com.avetti.simplemerce.pagetiles.datatransfer.PageTileDictionaryDTO;
import com.avetti.simplemerce.store.domain.StoreItemForCat;


public class NWStoreControllerCustomDataImpl implements StoreControllerCustomData {
	protected final Log logger = LogFactory.getLog(NWStoreControllerCustomDataImpl.class);
	private InventoryMultiService inventoryMultiService;
	
	public void setInventoryMultiService(InventoryMultiService inventoryMultiService) {
		this.inventoryMultiService = inventoryMultiService;
	}

	@Override
	public void setCustomData(HttpServletRequest request, Map model) {
		try {
			
			PageTileDictionaryDTO pageTileDictionaryDTO  = (PageTileDictionaryDTO)
			    model.get("pageTileDictionaryDTO");
			logger.info("pageTileDictionaryDTO found: " + pageTileDictionaryDTO);
			PageTileDTO childItemsDTO = (PageTileDTO) pageTileDictionaryDTO.getPageTile("childItemsDTO");
			logger.info("childItemsDTO found: " + childItemsDTO);
			ItemInput itemInput = new ItemInput();
			if (childItemsDTO != null) {
				Map<String, Object[]> itemsMap = new HashMap<String, Object[]>();
				if(childItemsDTO.getItems() != null) {
					for(Iterator<Object> iter = childItemsDTO.getItems().iterator();
						iter.hasNext();) {
						Object storeItemObject = iter.next();
						if(storeItemObject instanceof Object[]) {
							StoreItemForCat storeItemForCat=						
								 (StoreItemForCat)((Object[]) storeItemObject)[0];
							logger.info("StoreItemObject is:" + ((Object[]) storeItemObject).toString());
							logger.info("StoreItemForCat is:" + storeItemForCat);
							if (null == itemInput.getCode()){
								itemInput.setCode(storeItemForCat.getCode());
							} else {
								itemInput.setCode(
										itemInput.getCode()+","+storeItemForCat.getCode());
							}
							if (null == itemInput.getVendorid() ){
								itemInput.setVendorid(storeItemForCat.getVendorid());
							}
							logger.info("adding items:"+storeItemForCat.getCode());
							logger.info("store item object: "+((Object[]) storeItemObject).length);
							itemsMap.put(storeItemForCat.getCode(), ((Object[]) storeItemObject));
							 
						}	
					}
					ItemInventoryResponse itemInventoryResponse = 
						inventoryMultiService.GetItemInventoryArray(itemInput);
					for (ItemInventory itemInventory : itemInventoryResponse.getInventories()) {
						logger.info("instock is:"+itemInventory.getInstock());
    					logger.info("code is:"+itemInventory.getCode());
						Object[] storeItem = itemsMap.get(itemInventory.getCode());
						if (null == storeItem ){
							storeItem = itemsMap.get(itemInventory.getCode().replace(".", "-"));
						}
						logger.debug("instock is:"+itemInventory.getInstock());
						logger.debug("storeItem:"+storeItem);
							storeItem[6] = (Short.parseShort(String.valueOf(itemInventory.getInstock())));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("there was issue with calling NW webservices. skipped NW webservice inventory logic!!");
		}
	}
}
