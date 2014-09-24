package com.avetti.commerce.xfire;

import java.lang.reflect.Proxy;

import javax.xml.namespace.QName;

import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.aegis.type.Type;
import org.codehaus.xfire.aegis.type.TypeMapping;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.soap.SoapConstants;
import org.springframework.beans.factory.InitializingBean;


public class CustomDateTypeRegister implements InitializingBean {
	private ObjectServiceFactory serviceFactory;
	private Object[] serviceClients;

	public void setServiceFactory(ObjectServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public void setServiceClients(Object[] serviceClients) {
		this.serviceClients = serviceClients;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Type customDateType = createCustomDateType();
		
		for (Object serviceClient : serviceClients) {
			Service service = ((XFireProxy)Proxy.getInvocationHandler(serviceClient)).getClient().getService();
			TypeMapping typeMapping = ((AegisBindingProvider) serviceFactory.getBindingProvider())
					.getTypeMapping(service);
			
			Type dateType = typeMapping.getType(new QName(SoapConstants.XSD, "date"));
	
			if (dateType != null) {
				typeMapping.removeType(dateType);
				typeMapping.register(customDateType);
			}
		}
		
	}
	
	private Type createCustomDateType() {
		Type dateType = new CustomDateType();
		dateType.setTypeClass(dateType.getClass());
		dateType.setSchemaType(new QName(SoapConstants.XSD, "date"));
		
		return dateType;
	}
}
