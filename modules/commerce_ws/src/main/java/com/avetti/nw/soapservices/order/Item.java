package com.avetti.nw.soapservices.order;

public class Item {
	private String itemNumber;
	private String description1;
	private String description2;
	private Integer orderQty;
	private Integer shipQty;
	private Integer bOQty;
	private String uOM;
	private Double sellPrice;
	private Double total;
	private String lineType;

	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getDescription1() {
		return description1;
	}
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	public Integer getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}
	public Integer getShipQty() {
		return shipQty;
	}
	public void setShipQty(Integer shipQty) {
		this.shipQty = shipQty;
	}
	public Integer getbOQty() {
		return bOQty;
	}
	public void setbOQty(Integer bOQty) {
		this.bOQty = bOQty;
	}
	public String getuOM() {
		return uOM;
	}
	public void setuOM(String uOM) {
		this.uOM = uOM;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
}
