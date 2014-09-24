package com.avetti.commerce.xfire;

import java.text.SimpleDateFormat;

import org.apache.commons.validator.GenericValidator;
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.aegis.MessageReader;
import org.codehaus.xfire.aegis.type.java5.XMLGregorianCalendarType;
import org.codehaus.xfire.fault.XFireFault;
import org.springframework.util.StringUtils;

public class CustomDateType extends XMLGregorianCalendarType {

	private final String DEFAULT_DATE_FORMAT = "MM-dd-yyyy";
	
	private final String DEFAULT_DATE_FORMAT_EXPRESSION = 
			"^(0[1-9]|1[012])[-]?(0[1-9]|[12][0-9]|3[01])[-]?(19|20)\\d{2}$";
	
	private final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
	
	private final String SIMPLE_DATE_FORMAT_EXPRESSION = 
			"^(19|20)\\d{2}[-]?(0[1-9]|1[012])[-]?(0[1-9]|[12][0-9]|3[01])$";
	
	private final String XML_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	private SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
	private SimpleDateFormat xmlDateFormatter = new SimpleDateFormat(XML_DATE_FORMAT);
	
	public void setDateFormat(String dateFormat) {
		dateFormatter = new SimpleDateFormat(dateFormat);
	}
	
	@Override
	public Object readObject(MessageReader reader, MessageContext context)
			throws XFireFault {
		if(!StringUtils.hasLength(reader.getValue()))
			return null;
		try {
			if(GenericValidator.matchRegexp(reader.getValue(), 
					DEFAULT_DATE_FORMAT_EXPRESSION))
				return dateFormatter.parse(reader.getValue());
			if(GenericValidator.matchRegexp(reader.getValue(), 
					SIMPLE_DATE_FORMAT_EXPRESSION))
				return simpleDateFormatter.parse(reader.getValue());
			return xmlDateFormatter.parse(translateDate(reader.getValue()));
		} catch (Exception e) {
		}
		
		return super.readObject(reader, context);
	}
	
	private String translateDate(String date){
		StringBuilder  st = new StringBuilder(date);
		String timeZone = st.substring(st.length()-6, st.length());
		st.delete(st.length()-6, st.length());
		timeZone=timeZone.replaceAll(":", "");
		st.append(timeZone);
		return st.toString();
	}

}
