
package com.mids.auth.role.controller;

import io.swagger.annotations.*;
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
import com.mids.auth.role.request.RoleRequest;
import com.mids.auth.role.service.PermissionService;
import com.mids.auth.role.service.RoleService;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Api(tags = "permission", description = "permission management")
@RestController
@RequestMapping(value = "api/auth/permission")
@Validated
public class PermissionController {

	private final PermissionService permissionService;

	@Autowired
	public PermissionController(PermissionService permissionService) {

		this.permissionService = permissionService;

	}

	@ApiOperation(value = "Add permission", notes = "The endpoint will add a permission for any role")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addPermission(
			@ApiParam(value = "Permission object", required = true) @Valid @RequestBody PermissionRequest body) {
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

	@ApiOperation(value = "Get the list of permission roles", notes = "The endpoint will return the list of permission roles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of permission") })
	@GetMapping
	public ResponseEntity<PageResponse<PermissionRequest>> getAllPermissions(
			@ApiParam(value = "Page id", type = "integer", example = "0") @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@ApiParam(value = "Number of records", type = "integer", example = "10") @Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {
		return ResponseEntity.ok(permissionService.getAllPermissions(page, limit));
	}

	@ApiOperation(value = "Get the list of permission roles by using application id ", notes = "The endpoint will return the list of permission roles by using application id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of roles by using application id") })
	@GetMapping(value = "{applicationId}")
	public ResponseEntity<List<PermissionRequest>> getPermissionByApplicationId(
			@ApiParam(value = "Application of the object", required = true) @PathVariable String applicationId) {
		List<PermissionRequest> role = permissionService.getPermissionByApplicationId(applicationId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, applicationId));
		}
	}

	@ApiOperation(value = "Get the list of permission roles by using application id and role id", notes = "The endpoint will return the list of permission roles by using application id and role id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of roles by using application id and role id") })
	@GetMapping(value = "{applicationId}/{roleId}")
	public ResponseEntity<List<PermissionRequest>> getPermissionByApplicationId(
			@ApiParam(value = "Application of the object", required = true) @PathVariable String applicationId,
			@ApiParam(value = "UUID of the object", required = true) @PathVariable UUID roleId) {
		List<PermissionRequest> role = permissionService.getPermissionByApplicationRoleId(applicationId, roleId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, applicationId));
		}
	}

	@ApiOperation(value = "Update the permission of any role", notes = "The endpoint will update a permission of any role")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Permission object") })
	@PutMapping(value = "{id}")
	public ResponseEntity<PermissionRequest> updatePermission(
			@ApiParam(value = "Permission object", required = true) @PathVariable UUID id,
			@ApiParam(value = "Permission object", required = true) @Valid @RequestBody PermissionRequest body) {
		PermissionRequest permission = permissionService.updatePermission(id, body);
		if (permission != null) {
			return ResponseEntity.ok(permission);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_ID_NOT_FOUND, body.getId()));
		}
	}

	@ApiOperation(value = "Delete all the permission of Application", notes = "The endpoint will delete all the permission of any Application")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successful operation") })
	@DeleteMapping(value = "{applicationId}")
	public ResponseEntity<SuccessResponse> deletePermission(
			@ApiParam(value = "Application Id of the permission", required = true) @PathVariable String applicationId) {
		boolean success = permissionService.deletePermissionByApplicationId(applicationId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		}
	}

	@ApiOperation(value = "Delete the particular permission of any application", notes = "The endpoint will delete the particular permission of any application")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successful operation") })
	@DeleteMapping(value = "{applicationId}/{roleId}")
	public ResponseEntity<SuccessResponse> deletePermission(
			@ApiParam(value = "Application id of the permission", required = true) @PathVariable String applicationId,
			@ApiParam(value = "UUID of the role object", required = true) @PathVariable UUID roleId) {
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
