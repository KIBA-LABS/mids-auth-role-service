package com.mids.auth.role.service;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.request.RoleRequest;

	public interface RoleService {
	    PageResponse<RoleRequest> getAllRoles(Integer page, Integer limit);

	    String addRole(RoleRequest role);

	    RoleRequest getRole(UUID id);

	    RoleRequest updateRole(UUID id, RoleRequest role);

		boolean deleteRoleByApplicationId(String applicationId);
		
		boolean deleteRoleByApplicationRoleId(String applicationId,UUID roleId);

		 PageResponse<RoleRequest> getRoleByApplicationId(String applicationId, Integer page, Integer limit);

		List<RoleRequest> getRoleByApplicationUserId(String applicationId, String userId);

	   
	}



