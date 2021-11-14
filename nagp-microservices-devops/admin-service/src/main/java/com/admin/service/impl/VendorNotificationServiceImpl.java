package com.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.ServiceExpert;
import com.admin.proxy.service.VendorServiceProxy;

@Service
public class VendorNotificationServiceImpl {

	@Autowired
	VendorServiceProxy proxy;
	
	public List<ServiceExpert> findExpertsByProfession(@PathVariable List<com.admin.enums.Service> ids) {
		return proxy.findExpertsByProfession(ids);
	}
}
