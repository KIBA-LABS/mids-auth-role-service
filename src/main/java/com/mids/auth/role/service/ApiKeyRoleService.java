package com.mids.auth.role.service;


import com.mids.auth.role.request.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ApiKeyRoleService {

	String addApiKeyRole(UserRoleRequest userRoleRequest, String applicationId, UUID apiKeyId);

	String addApiKeyPermission(UserPermissionRequest body, String appId, UUID apiKeyId);

	List<RoleRequest> getRoleByApplicationApiKeyId(String applicationId, UUID apiKeyId);

	String addApiKeyRolePermission(@Valid UserRolePermissionRequest body, String appId, UUID apiKeyId);

	boolean deleteRoleByApplicationApiKeyId(String applicationId, UUID apiKeyId);
	
	boolean deletePermissionByApplicationApiKeyId(String applicationId, UUID apiKeyId);

	boolean addOrDeleteRoleByApplicationApiKeyId(UserAddDeleteRoleRequest body, String appId, UUID apiKeyId);
	
	
	boolean addOrDeletePermissionByApplicationApiKeyId(UserAddOrDeletePermissionRequest body, String appId, UUID apiKeyId);

	boolean putOrDeleteRoleByApplicationApiKeyId(UserRoleRequest body, String appId, UUID apiKeyId);

	boolean putOrDeletePermissionByApplicationApiKeyId(UserPermissionRequest body, String appId, UUID apiKeyId);

	boolean putOrDeleteRolesPermissionByApplicationApiKeyId( UserRolePermissionRequest body, String appId,
			UUID apiKeyId);

	boolean addOrDeleteRolePermissionByApplicationApiKeyId( UserAddDeleteRolePermissionRequest body, String appId,
			UUID apiKeyId);
}
