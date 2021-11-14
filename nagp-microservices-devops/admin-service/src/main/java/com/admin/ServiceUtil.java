package com.admin;

import java.util.Map;

import com.admin.enums.Service;

public class ServiceUtil {

	private static Map<Long, Service> serviceMap;
	
	public static Map<Long, Service> getServiceName() {
		serviceMap.put(1L, Service.ELECTRICITY_INSTALLATION_SERVICES);
		serviceMap.put(2L, Service.ELECTRICITY_REPAIRS_AND_FIXES);
		serviceMap.put(3L, Service.SALON_FOR_MEN_CLEAN_SHAVE);
		serviceMap.put(4L, Service.SALON_FOR_MEN_HAIR_CUT);
		serviceMap.put(5L, Service.SALON_FOR_WOMEN_HAIR_CUT);
		serviceMap.put(6L, Service.SALON_FOR_WOMEN_TAN_REMOVAL);
		serviceMap.put(7L, Service.YOGA_AMATEUR);
		serviceMap.put(8L, Service.YOGA_PROFESSIONAL);
		return serviceMap;
	}
}
