package com.mids.auth.role.service;

import java.util.List;
import java.util.UUID;

import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.request.PermissionRequest;
import com.mids.auth.role.request.RoleRequest;

public interface PermissionService {

	String addPermission(PermissionRequest role);

	PageResponse<PermissionRequest> getAllPermissions(Integer pageNumber, Integer recordsPerPage);

	PermissionRequest getPermission(UUID id);

	boolean deletePermission(UUID id);

	PermissionRequest updatePermission(UUID id,PermissionRequest permission);

	List<PermissionRequest> getPermissionByApplicationId(int applicationId);

	List<PermissionRequest> getPermissionByApplicationRoleId(int applicationId, UUID roleId);

	boolean deletePermissionByApplicationId(int applicationId);

	boolean deletePermissionByApplicationRoleId(int applicationId, UUID roleId);

}
