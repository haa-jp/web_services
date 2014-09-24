package com.avetti.nw.soapservices.item;


public class ItemPriceAvail {
	private String itemNumber;
	private Double dealerPrice;
	private Double retailPrice;
	private Double contractPrice;
	private Integer available;

	public ItemPriceAvail(String itemNumber, Double dealerPrice,
			Double retailPrice, Double contractPrice, Integer available) {
		super();
		this.itemNumber = itemNumber;
		this.dealerPrice = dealerPrice;
		this.retailPrice = retailPrice;
		this.contractPrice = contractPrice;
		this.available = available;
	}

	public ItemPriceAvail() {
		super();
	}

	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public Double getDealerPrice() {
		return dealerPrice;
	}
	public void setDealerPrice(Double dealerPrice) {
		this.dealerPrice = dealerPrice;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Double getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
}
