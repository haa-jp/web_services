package com.avetti.commerce.module.inventorybaws.inventorybows;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avetti.commerce.domainmodel.Inventory;
import com.avetti.commerce.domainmodel.Items;
import com.avetti.commerce.items.ItemBO;
import com.avetti.commerce.module.inventorybaws.service.InventoryMultiService;
import com.avetti.commerce.module.inventorybaws.service.InventoryService;
import com.avetti.commerce.module.inventorybaws.service.ItemInput;
import com.avetti.commerce.module.inventorybaws.service.ItemInventory;
import com.avetti.commerce.module.inventorybaws.service.ItemInventoryResponse;
import com.avetti.simplemerce.store.domain.StoreItemForCat;

public class InventoryBOImpl {
	
	private static final Log LOG = LogFactory.getLog(InventoryBOImpl.class);
	
	private InventoryService service;
	private InventoryMultiService inventoryMultiService;
	
	public void setInventoryMultiService(InventoryMultiService inventoryMultiService) {
		this.inventoryMultiService = inventoryMultiService;
	}
	
	private ItemBO itemBO;

	public void setItemBO(ItemBO itemBO) {
		this.itemBO = itemBO;
	}

	public void setService(InventoryService service) {
		this.service = service;
	}
	
	/**
	 * Trying to search Inventory at the web service
	 * @param vendorId
	 * @param itemCode
	 * @return <b>com.avetti.commerce.domainmodel.Inventory</b> entity 
	 * or <b>null</b> if argument is null
	 */
	public Inventory getInventory(String vendorId, String itemCode){
		ItemInput ii = new ItemInput();
		ii.setVendorid(vendorId);
		ii.setCode(itemCode);
		ItemInventory wsResponse = null;
		try{
			LOG.info("calling GetItemInventoryService from:" + Thread.currentThread().getStackTrace());
			wsResponse = service.GetItemInventory(ii);
		} catch (org.codehaus.xfire.XFireRuntimeException e){
			LOG.warn("Something gone wrong with Inventory web service: " + e.getMessage());
		}
		return getInventoryFromWSResponse(vendorId, itemCode, wsResponse);
	}
	
	/**
	 * Converts Inventory web service response to Inventory entity
	 * not set 
	 * @param vendorId
	 * @paam itemCode
	 * @param ii
	 * @return <b>Inventory</b> entity, <b>null</b> if argument is null
	 */
	private Inventory getInventoryFromWSResponse(String vendorId, String itemCode, ItemInventory ii){
		if (ii == null || vendorId == null || itemCode == null){
			return null;
		}
		Inventory inv = new Inventory();
		Items item = itemBO.find(vendorId, itemCode);
		if ( item != null ){
			inv.setItemid(item.getItemId());
		}		
		inv.setVendorid(vendorId);
		inv.setCode(itemCode);
		inv.setDropshipdays(ii.getDropshipdays());
		inv.setDropshipminqty(ii.getDropshipminqty());
		inv.setInstock(ii.getInstock());
		inv.setMinorderqty(ii.getMinqty());
		inv.setMinreorderqty(ii.getMinqty()); //FIXME : is it necessary?
		inv.setNextshipdate(ii.getNextshipdate().toGregorianCalendar().getTime());
		inv.setNextshipqty(ii.getNextshipqty());
				
		return inv;
	}
	public Map<String, Inventory> getInventories(String vendorId, List<String> itemCodes){
		ItemInput itemInput = new ItemInput();
		for (String itemCode : itemCodes) {
			if (null == itemInput.getCode()){
				itemInput.setCode(itemCode);
			} else {
				itemInput.setCode(
						itemInput.getCode()+","+itemCode);
			}
			if (null == itemInput.getVendorid() ){
				itemInput.setVendorid(vendorId);
			} 
		}
		ItemInventoryResponse itemInventoryResponse = 
			inventoryMultiService.GetItemInventoryArray(itemInput);
		Map<String, Inventory> inventoryMap = new HashMap<String, Inventory>();
		for (ItemInventory itemInventory : itemInventoryResponse.getInventories()) {
			Inventory inventory = new Inventory();
			inventory.setCode(itemInventory.getCode().replace(".", "-"));
			inventory.setDropshipdays(itemInventory.getDropshipdays());
			inventory.setDropshipminqty(itemInventory.getDropshipminqty());
			inventory.setInstock(itemInventory.getInstock());
			inventory.setMinorderqty(itemInventory.getMinqty());
			inventory.setNextshipqty(itemInventory.getNextshipqty());
			inventory.setNextshipdate(itemInventory.getNextshipdate().toGregorianCalendar().getTime());
            inventory.setVendorid(vendorId);
			inventoryMap.put(inventory.getCode(), inventory);
		}
		return inventoryMap;
	}

}
