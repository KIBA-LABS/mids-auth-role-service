package com.mids.auth.role.service;
import java.util.List;
import java.util.UUID;

import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.request.RoleRequest;

	public interface RoleService {
	    PageResponse<RoleRequest> getAllRoles(Integer page, Integer limit);

	    String addRole(RoleRequest role);

	    RoleRequest getRole(UUID id);

	    RoleRequest updateRole(UUID id, RoleRequest role);

		boolean deleteRoleByApplicationId(int appplicationId);
		
		boolean deleteRoleByApplicationRoleId(int appplicationId,UUID roleId);

		List<RoleRequest> getRoleByApplicationId(int applicationid);

	   
	}



