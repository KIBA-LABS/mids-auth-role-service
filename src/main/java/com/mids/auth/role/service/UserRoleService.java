package com.mids.auth.role.service;



import com.mids.auth.role.request.UserRoleRequest;

public interface UserRoleService {

	String addUserRole(UserRoleRequest userRoleRequest, String applicationId, String userId);

}
