package com.avetti.nw.soapservices.customer;

import java.util.List;

public class CustomerOrdersResponse {
	private Integer orderCount;
	private List<Order> orders;

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
