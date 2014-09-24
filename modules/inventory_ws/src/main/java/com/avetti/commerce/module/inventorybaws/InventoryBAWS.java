package com.avetti.commerce.module.inventorybaws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avetti.commerce.domainmodel.Inventory;
import com.avetti.commerce.inventory.InventoryBA;
import com.avetti.commerce.inventory.impl.InventoryBAImpl;
import com.avetti.commerce.module.inventorybaws.inventorybows.InventoryBOImpl;
import com.avetti.commerce.module.inventorybaws.service.InventoryMultiService;
import com.avetti.simplemerce.store.businessobjects.StoreItemBO;
import com.avetti.simplemerce.store.domain.StoreItem;

public class InventoryBAWS extends InventoryBAImpl implements InventoryBA  {
	
	private InventoryBOImpl inventoryBOws;
	
	private StoreItemBO storeItemBO;

	public void setStoreItemBO(StoreItemBO storeItemBO) {
		this.storeItemBO = storeItemBO;
	}

	public InventoryBOImpl getInventoryBOws() {
		return inventoryBOws;
	}

	public void setInventoryBOws(InventoryBOImpl inventoryBOws) {
		this.inventoryBOws = inventoryBOws;
	}
	private InventoryMultiService inventoryMultiService;
	
	public void setInventoryMultiService(InventoryMultiService inventoryMultiService) {
		this.inventoryMultiService = inventoryMultiService;
	}
	
	@Override
	public Inventory find(String vendorId, String inventoryCode) {
		if (vendorId == null || vendorId.trim().length() == 0
				|| inventoryCode == null || inventoryCode.trim().length() == 0){
			return null;
		}		
		return getInventoryBOws().getInventory(vendorId, inventoryCode);
	}

	@Override
	public List<Inventory> findByItemId(String vendorId, List<Long> itemIdList) {
		if (itemIdList == null || itemIdList.isEmpty() || vendorId == null){
			return Collections.EMPTY_LIST;
		}
		List<Inventory> result = new ArrayList<Inventory>();
		for (Long l : itemIdList){
			StoreItem item = storeItemBO.getStoreItemById(l);
			if (item == null) continue;
			Inventory inv = getInventoryBOws().getInventory( vendorId, item.getCode());
			if (inv != null){
				result.add(inv);
			}
		}		
		return result;
	}

	@Override
	public List<Inventory> findByItemCode(String vendorId, String itemCompCode) {
		if (itemCompCode == null || vendorId == null){
			return Collections.EMPTY_LIST;
		}
		List<Inventory> result = new ArrayList<Inventory>();
		Inventory inv = getInventoryBOws().getInventory( vendorId, itemCompCode);
		if (inv != null){
			result.add(inv);
		}
		return result;
	}

	@Override
	public List<Inventory> findByItemCode(String vendorId, List<String> itemCompCodeList) {
		if (itemCompCodeList == null || itemCompCodeList.isEmpty() || vendorId == null){
			return Collections.EMPTY_LIST;
		}
		List<Inventory> result = new ArrayList<Inventory>();
		for (String code : itemCompCodeList){
			Inventory inv = getInventoryBOws().getInventory( vendorId, code);
			if ( inv != null){
				result.add(inv);
			}
		}		
		return result;
	}
	
	@Override
	public List<Inventory> findByItemId(String vendorId, Long itemId) {
		if (itemId == null || vendorId == null){
			return Collections.EMPTY_LIST;
		}
		List<Inventory> result = new ArrayList<Inventory>();
		StoreItem item = storeItemBO.getStoreItemById(itemId);
		if ( item == null){
			return Collections.EMPTY_LIST;
		}
		Inventory inv = getInventoryBOws().getInventory( vendorId, item.getCode()); 
		if ( inv != null){
			result.add(inv);
		}
		return result;
	}
	
	@Override
	public List<Inventory> findAllAttributed(String vendorId, String itemCode) {
		if (itemCode == null || vendorId == null) {
			return Collections.EMPTY_LIST;
		}
		List<Inventory> result = new ArrayList<Inventory>();
		Inventory inv = getInventoryBOws().getInventory(vendorId, itemCode);
		if (inv != null){
			result.add(inv);
		}
		return result;

	}
	public Map<String, Inventory> findByItemCodes(String vendorId,
			List<String> itemCompCodes) {
		Map<String, Inventory> inventoryMap = inventoryBOws.getInventories(vendorId, itemCompCodes);
		return inventoryMap;
	}
}
