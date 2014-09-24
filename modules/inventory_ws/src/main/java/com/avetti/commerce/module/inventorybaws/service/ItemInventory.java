package com.avetti.commerce.module.inventorybaws.service;

import javax.xml.datatype.XMLGregorianCalendar;

public class ItemInventory {
	
	private String vendorid;
	
	private String code;
	
	private int instock;
	
	private int nextshipqty;
	
	private XMLGregorianCalendar nextshipdate;
	
	private int dropshipminqty;
	
	private int dropshipdays;
	
	public XMLGregorianCalendar getNextshipdate() {
		return nextshipdate;
	}

	public void setNextshipdate(XMLGregorianCalendar nextshipdate) {
		this.nextshipdate = nextshipdate;
	}

	private int minqty;

	public String getVendorid() {
		return vendorid;
	}

	public String getCode() {
		return code;
	}

	public int getInstock() {
		return instock;
	}

	public int getNextshipqty() {
		return nextshipqty;
	}

	public int getDropshipminqty() {
		return dropshipminqty;
	}

	public int getDropshipdays() {
		return dropshipdays;
	}

	public int getMinqty() {
		return minqty;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInstock(int instock) {
		this.instock = instock;
	}

	public void setNextshipqty(int nextshipqty) {
		this.nextshipqty = nextshipqty;
	}

	public void setDropshipminqty(int dropshipminqty) {
		this.dropshipminqty = dropshipminqty;
	}

	public void setDropshipdays(int dropshipdays) {
		this.dropshipdays = dropshipdays;
	}

	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}

}
