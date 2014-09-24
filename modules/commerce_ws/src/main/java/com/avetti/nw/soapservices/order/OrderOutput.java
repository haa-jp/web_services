package com.avetti.nw.soapservices.order;

import java.util.List;


public class OrderOutput {
	private Header header;
	private List<Item> detail;
	private List<Shipment> shipping;
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public List<Item> getDetail() {
		return detail;
	}
	public void setDetail(List<Item> detail) {
		this.detail = detail;
	}
	public List<Shipment> getShipping() {
		return shipping;
	}
	public void setShipping(List<Shipment> shipping) {
		this.shipping = shipping;
	}
}
