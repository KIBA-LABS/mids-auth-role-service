package com.mids.auth.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.dto.SuccessResponse;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.request.PermissionRequest;
import com.mids.auth.role.request.RoleRequest;
import com.mids.auth.role.request.UserRoleRequest;
import com.mids.auth.role.service.PermissionService;
import com.mids.auth.role.service.RoleService;
import com.mids.auth.role.service.UserRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/auth/roles")
@Validated
public class RoleController {

	private final RoleService roleService;

	private final UserRoleService userRoleService;

	@Autowired
	public RoleController(RoleService roleService, UserRoleService userRoleService) {
		this.roleService = roleService;
		this.userRoleService = userRoleService;

	}

	@Operation(summary = "Get the list of application roles", description = "The endpoint will return the list of application roles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping
	public ResponseEntity<PageResponse<RoleRequest>> getAllRoles(
			 @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {
		return ResponseEntity.ok(roleService.getAllRoles(page, limit));
	}

	@Operation(summary = "Add role", description = "The endpoint will add a role for application")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addRole(
			 @Valid @RequestBody RoleRequest body) {

		System.out.println("inside addrole controller");
		roleService.addRole(body);
		SuccessResponse successResponse = new SuccessResponse(StringConstant.ROLE_IS_CREATED);
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Get the list of application roles using application id ", description = "The endpoint will return the list of application roles by using application id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}")
	public ResponseEntity<PageResponse<RoleRequest>> getRoleByApplicationId(
			 @PathVariable String applicationId,
			 @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			 @Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {

		return ResponseEntity.ok(roleService.getRoleByApplicationId(applicationId, page, limit));

	}

	@Operation(summary = "Get the list of application roles using application id and userId ", description = "The endpoint will return the list of application roles by using application and user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}/users/{userId}")
	public ResponseEntity<List<RoleRequest>> getRoleByApplicationUserId(
			 @PathVariable String applicationId,
			 @PathVariable String userId) {
		List<RoleRequest> role = roleService.getRoleByApplicationUserId(applicationId, userId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_APPLICATIONID_NOT_FOUND, applicationId));
		}
	}
	
	
	@Operation(summary = "Get the list of application roles and permissions using application id and userId ", description = "The endpoint will return the list of application roles and permissions by using application and user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "permissions/applications/{applicationId}/users/{userId}")
	public ResponseEntity<List<RoleRequest>> getRolePermissionByApplicationUserId(
			 @PathVariable String applicationId,
			 @PathVariable String userId) {
		List<RoleRequest> role = roleService.getRoleByApplicationUserId(applicationId, userId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_APPLICATIONID_NOT_FOUND, applicationId));
		}
	}

	@Operation(summary = "Update the application role", description = "The endpoint will update a role of application")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "{id}")
	public ResponseEntity<RoleRequest> updateRole(
			 @PathVariable UUID id,
			 @Valid @RequestBody RoleRequest body) {
		RoleRequest role = roleService.updateRole(id, body);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, body.getId()));
		}
	}
	
	@Operation(summary = "Get the application role by roleId", description = "The endpoint will get a role by roleId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "{roleId}")
	public ResponseEntity<RoleRequest> updateRole(
			 @PathVariable UUID roleId) {
		RoleRequest role = roleService.getRoleByRoleId(roleId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, roleId));
		}
	}

	@Operation(summary = "Delete the application role ", description = "The endpoint will delete a role of any application ")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "applications/{applicationId}")
	public ResponseEntity<SuccessResponse> deleteRole(
			 @PathVariable String applicationId) {
		boolean success = roleService.deleteRoleByApplicationId(applicationId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		}
	}

	@Operation(summary = "Delete the application role using role id", description = "The endpoint will delete a role of any application by role id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "applications/{applicationId}/roles/{roleId}")
	public ResponseEntity<SuccessResponse> deleteRole(
			 @PathVariable String applicationId,
			 @PathVariable UUID roleId) {
		String success = roleService.deleteRoleByApplicationRoleId(applicationId, roleId);
		if ("Success".equalsIgnoreCase(success)) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);
		} else if ("appId".equalsIgnoreCase(success)) {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		}
		
		else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, roleId));
		}
	}

}
