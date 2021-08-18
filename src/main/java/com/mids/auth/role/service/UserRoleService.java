package com.mids.auth.role.service;



import javax.validation.Valid;

import com.mids.auth.role.request.UserAddDeleteRolePermissionRequest;
import com.mids.auth.role.request.UserAddDeleteRoleRequest;
import com.mids.auth.role.request.UserAddOrDeletePermissionRequest;
import com.mids.auth.role.request.UserPermissionRequest;
import com.mids.auth.role.request.UserRolePermissionRequest;
import com.mids.auth.role.request.UserRoleRequest;

public interface UserRoleService {

	String addUserRole(UserRoleRequest userRoleRequest, String applicationId, String userId);

	String addUserPermission(UserPermissionRequest body, String appId, String userId);

	String addUserRolePermission(@Valid UserRolePermissionRequest body, String appId, String userId);

	boolean deleteRoleByApplicationUserId(String applicationId, String userId);
	
	boolean deletePermissionByApplicationUserId(String applicationId, String userId);

	boolean addOrDeleteRoleByApplicationUserId(UserAddDeleteRoleRequest body, String appId, String userId);
	
	
	boolean addOrDeletePermissionByApplicationUserId(UserAddOrDeletePermissionRequest body, String appId, String userId);

	boolean putOrDeleteRoleByApplicationUserId(UserRoleRequest body, String appId, String userId);

	boolean putOrDeletePermissionByApplicationUserId(UserPermissionRequest body, String appId, String userId);

	boolean putOrDeleteRolesPermissionByApplicationUserId(@Valid UserRolePermissionRequest body, String appId,
			String userId);

	boolean addOrDeleteRolePermissionByApplicationUserId(@Valid UserAddDeleteRolePermissionRequest body, String appId,
			String userId);

}
