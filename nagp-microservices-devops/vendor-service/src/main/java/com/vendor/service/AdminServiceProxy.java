package com.vendor.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vendor.model.ServiceExpert;

@FeignClient(name = "admin-service")
public interface AdminServiceProxy {

	@PostMapping("admins/checkBookingsWithNoResponseStatus")
	public void checkForNoResponseBookings(@RequestBody ServiceExpert expert);

}
