package com.mids.auth.customer.service;

import com.mids.auth.customer.dto.PageResponse;
import com.mids.auth.customer.request.RegisterRequest;

public interface RegistrationService {
	
	public String registerCustomer(RegisterRequest register);

	PageResponse<RegisterRequest> getAllCustomers(Integer pageNumber, Integer recordsPerPage);

	RegisterRequest getCustomer(String userId);

	boolean deleteCustomer(String clientId);

	RegisterRequest updateCustomer(RegisterRequest register);

}
