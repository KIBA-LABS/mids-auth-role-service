
package com.mids.auth.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.dto.SuccessResponse;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.request.PermissionRequest;

import com.mids.auth.role.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/auth/permission")
@Validated
public class PermissionController {

	private final PermissionService permissionService;

	@Autowired
	public PermissionController(PermissionService permissionService) {

		this.permissionService = permissionService;

	}

	@Operation(summary = "Add permission", description = "The endpoint will add a permission for any role")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addPermission(@Valid @RequestBody PermissionRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside addrole controller");
		String response = permissionService.addPermission(body);
		if ("Role".equalsIgnoreCase(response)) {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, body.getRole()));

		} else {
			successResponse = new SuccessResponse(StringConstant.PERMISSION_IS_CREATED);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Get the list of permission roles", description = "The endpoint will return the list of permission roles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping
	public ResponseEntity<PageResponse<PermissionRequest>> getAllPermissions(
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {
		return ResponseEntity.ok(permissionService.getAllPermissions(page, limit));
	}

	@Operation(summary = "Get the list of permission roles by using application id ", description = "The endpoint will return the list of permission roles by using application id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}")
	public ResponseEntity<List<PermissionRequest>> getPermissionByApplicationId(@PathVariable String applicationId) {
		List<PermissionRequest> role = permissionService.getPermissionByApplicationId(applicationId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, applicationId));
		}
	}

	@Operation(summary = "Get the list of permission roles by using application id and role id", description = "The endpoint will return the list of permission roles by using application id and role id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}/roles/{roleId}")
	public ResponseEntity<List<PermissionRequest>> getPermissionByApplicationRoleId(@PathVariable String applicationId,
			@PathVariable UUID roleId) {
		List<PermissionRequest> role = permissionService.getPermissionByApplicationRoleId(applicationId, roleId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, applicationId));
		}
	}

	@Operation(summary = "Get the list of permission roles by using application id and user id", description = "The endpoint will return the list of permission roles by using application id and user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}/users/{userId}")
	public ResponseEntity<List<PermissionRequest>> getPermissionByApplicationUserId(@PathVariable String applicationId,
			@PathVariable String userId) {
		List<PermissionRequest> role = permissionService.getPermissionByApplicationUserId(applicationId, userId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, applicationId));
		}
	}

	@Operation(summary = "Update the permission of any role", description = "The endpoint will update a permission of any role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "{id}")
	public ResponseEntity<PermissionRequest> updatePermission(@PathVariable UUID id,
			@Valid @RequestBody PermissionRequest body) {
		PermissionRequest permission = permissionService.updatePermission(id, body);
		if (permission != null) {
			return ResponseEntity.ok(permission);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, body.getId()));
		}
	}
	
	
	@Operation(summary = "get the permission by permissionId", description = "The endpoint will get the permission by permissionId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "{permissionId}")
	public ResponseEntity<PermissionRequest> updatePermission(@PathVariable UUID permissionId) {
		PermissionRequest permission = permissionService.getPermissionByPermissionId(permissionId);
		if (permission != null) {
			return ResponseEntity.ok(permission);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND,permissionId));
		}
	}

	@Operation(summary = "Delete all the permission of Application", description = "The endpoint will delete all the permission of any Application")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "applications/{applicationId}")
	public ResponseEntity<SuccessResponse> deletePermission(@PathVariable String applicationId) {
		boolean success = permissionService.deletePermissionByApplicationId(applicationId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		}
	}
	
	@Operation(summary = "Delete all the permission of Application", description = "The endpoint will delete all the permission of any Application")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "{permissionId}")
	public ResponseEntity<SuccessResponse> deletePermissionByPermissionId(@PathVariable UUID permissionId) {
		boolean success = permissionService.deletePermissionByPermissionId(permissionId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, permissionId));
		}
	}

	@Operation(summary = "Delete the particular permission of any application by using role id", description = "The endpoint will delete the particular permission of any application by using role id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "applications/{applicationId}/roles/{roleId}")
	public ResponseEntity<SuccessResponse> deletePermission(@PathVariable String applicationId,
			@PathVariable UUID roleId) {
		boolean success = permissionService.deletePermissionByApplicationRoleId(applicationId, roleId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		}
	}

}
