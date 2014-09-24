package com.avetti.commerce.module.inventorybaws.service;

import java.util.ArrayList;
import java.util.List;

public class ItemInventoryResponse {
	private Integer inventoryCount;
	List<ItemInventory> inventories = new ArrayList<ItemInventory>();

	public Integer getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(Integer inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public List<ItemInventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<ItemInventory> inventories) {
		this.inventories = inventories;
	}
}
