package com.mids.auth.role.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.SuccessResponse;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.request.UserAddDeleteRolePermissionRequest;
import com.mids.auth.role.request.UserAddDeleteRoleRequest;
import com.mids.auth.role.request.UserAddOrDeletePermissionRequest;
import com.mids.auth.role.request.UserPermissionRequest;
import com.mids.auth.role.request.UserRolePermissionRequest;
import com.mids.auth.role.request.UserRoleRequest;
import com.mids.auth.role.service.UserRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "api/auth")
@Validated
public class UserRoleController {

	private final UserRoleService userRoleService;

	@Autowired
	public UserRoleController(UserRoleService userRoleService) {

		this.userRoleService = userRoleService;

	}

	@Operation(summary = "Add roles to any user", description = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("/roles/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRole(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "userId") String userId,

			@Valid @RequestBody UserRoleRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");

		String response = userRoleService.addUserRole(body, appId, userId);

		successResponse = new SuccessResponse(StringConstant.ROLE_IS_ADDED_TO_USER);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Add permissions to any user", description = "The endpoint will add a permissions to any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("/permissions/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserPermision(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "userId") String userId,

			@Valid @RequestBody UserPermissionRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");

		String response = userRoleService.addUserPermission(body, appId, userId);

		successResponse = new SuccessResponse(StringConstant.PERMISSION_IS_ADDED_TO_USER);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Add roles to any user", description = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("roles/permissions/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRolePermision(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "userId") String userId,

			@Valid @RequestBody UserRolePermissionRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");

		String response = userRoleService.addUserRolePermission(body, appId, userId);

		successResponse = new SuccessResponse(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_USER);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Delete the role for any user", description = "The endpoint will delete a role of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/roles/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> deleteRole(@PathVariable String appId, @PathVariable String userId) {
		boolean success = userRoleService.deleteRoleByApplicationUserId(appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete the permissions for any user", description = "The endpoint will delete a permissions of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> deletePermission( @PathVariable String appId,
			@PathVariable String userId) {
		boolean success = userRoleService.deleteRoleByApplicationUserId(appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete the roles and permissions for any user", description = "The endpoint will delete roles and permissions of any user of given applicationId")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/roles/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> deleteRoleAndPermissions( @PathVariable String appId,
			 @PathVariable String userId) {
		boolean success = userRoleService.deleteRoleByApplicationUserId(appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add the role for any user", description = "The endpoint will delete and add a role of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@PatchMapping(value = "/roles/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> AddOrDeleteRole( @PathVariable String appId,
			 @PathVariable String userId, @Valid @RequestBody UserAddDeleteRoleRequest body) {
		boolean success = userRoleService.addOrDeleteRoleByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add the permissions for any user", description = "The endpoint will delete and add the permissions of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PatchMapping(value = "/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> AddOrDeletePermission( @PathVariable String appId,
			 @PathVariable String userId,
			 @Valid @RequestBody UserAddOrDeletePermissionRequest body) {
		System.out.println("inside controller AddOrDeletePermission");

		boolean success = userRoleService.addOrDeletePermissionByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add any roles and permission  for any user", description = "The endpoint will delete and add any  roles and permission of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PatchMapping(value = "/roles/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> AddOrDeleteRolesPermission( @PathVariable String appId,
			@PathVariable String userId,
			 @Valid @RequestBody UserAddDeleteRolePermissionRequest body) {
		boolean success = userRoleService.addOrDeleteRolePermissionByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(
					new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Update the role for any user", description = "The endpoint will Update a role of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "/roles/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> PutOrDeleteRole(
			 @PathVariable String appId,
			 @PathVariable String userId,
			 @Valid @RequestBody UserRoleRequest body) {
		boolean success = userRoleService.putOrDeleteRoleByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_ADDED_TO_USER)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Update the permissions for any user", description = "The endpoint will Update a permissions of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> PutOrDeletePermission(@PathVariable String appId,
			 @PathVariable String userId,
			 @Valid @RequestBody UserPermissionRequest body) {
		boolean success = userRoleService.putOrDeletePermissionByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_ADDED_TO_USER)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Update the roles and permissions for any user", description = "The endpoint will Update the roles and permissions of any user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "roles/permissions/applications/{appId}/users/{userId}")
	public ResponseEntity<SuccessResponse> PutOrDeleteRolePermission(@PathVariable String appId,
			 @PathVariable String userId,
			@Valid @RequestBody UserRolePermissionRequest body) {
		boolean success = userRoleService.putOrDeleteRolesPermissionByApplicationUserId(body, appId, userId);
		if (success) {
			return new ResponseEntity(
					new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_USER)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

}
