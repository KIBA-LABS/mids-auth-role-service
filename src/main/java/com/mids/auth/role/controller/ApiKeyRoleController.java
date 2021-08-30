package com.mids.auth.role.controller;

import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.SuccessResponse;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.request.*;
import com.mids.auth.role.service.ApiKeyRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/auth")
@Validated
public class ApiKeyRoleController {

	private final ApiKeyRoleService apiKeyRoleService;

	@Autowired
	public ApiKeyRoleController(ApiKeyRoleService apiKeyRoleService) {

		this.apiKeyRoleService = apiKeyRoleService;

	}

	@Operation(summary = "Add roles to an apikey", description = "The endpoint will add a role to an apikey")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("/roles/applications/{appId}/apikeys/{apiKeyId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRole(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "apiKeyId") UUID apiKeyId,

			@Valid @RequestBody UserRoleRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");

		String response = apiKeyRoleService.addApiKeyRole(body, appId, apiKeyId);

		successResponse = new SuccessResponse(StringConstant.ROLE_IS_ADDED_TO_API_KEY);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Add permissions to an apikey", description = "The endpoint will add a permissions to an apikey")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("/permissions/applications/{appId}/apikeys/{apiKeyId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserPermision(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "apiKeyId") UUID apiKeyId,

			@Valid @RequestBody UserPermissionRequest body) {
		SuccessResponse successResponse;

		String response = apiKeyRoleService.addApiKeyPermission(body, appId, apiKeyId);

		successResponse = new SuccessResponse(StringConstant.PERMISSION_IS_ADDED_TO_API_KEY);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Add roles to an apikey", description = "The endpoint will add a role to an apikey")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successful operation") })
	@PostMapping("roles/permissions/applications/{appId}/apikeys/{apiKeyId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addApiKeyRolePermision(

			@PathVariable(value = "appId") String appId,

			@PathVariable(value = "apiKeyId") UUID apiKeyId,

			@Valid @RequestBody UserRolePermissionRequest body) {
		SuccessResponse successResponse;

		String response = apiKeyRoleService.addApiKeyRolePermission(body, appId, apiKeyId);

		successResponse = new SuccessResponse(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_USER);

		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	@Operation(summary = "Delete the role for an api key", description = "The endpoint will delete a role of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/roles/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> deleteApiKeyRole(@PathVariable String appId, @PathVariable UUID apiKeyId) {
		boolean success = apiKeyRoleService.deleteRoleByApplicationApiKeyId(appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete the permissions for an api key", description = "The endpoint will delete a permissions of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> deletePermission( @PathVariable String appId,
			@PathVariable UUID apiKeyId) {
		boolean success = apiKeyRoleService.deleteRoleByApplicationApiKeyId(appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete the roles and permissions for an api key", description = "The endpoint will delete roles and permissions of an api key of given applicationId")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@DeleteMapping(value = "/roles/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> deleteRoleAndPermissions( @PathVariable String appId,
			 @PathVariable UUID apiKeyId) {
		boolean success = apiKeyRoleService.deleteRoleByApplicationApiKeyId(appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
					HttpStatus.NO_CONTENT);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add the role for an api key", description = "The endpoint will delete and add a role of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successful operation") })
	@PatchMapping(value = "/roles/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> AddOrDeleteRole( @PathVariable String appId,
			 @PathVariable UUID apiKeyId, @Valid @RequestBody UserAddDeleteRoleRequest body) {
		boolean success = apiKeyRoleService.addOrDeleteRoleByApplicationApiKeyId(body, appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add the permissions for an api key", description = "The endpoint will delete and add the permissions of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PatchMapping(value = "/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> AddOrDeletePermission( @PathVariable String appId,
			 @PathVariable UUID apiKeyId,
			 @Valid @RequestBody UserAddOrDeletePermissionRequest body) {
		System.out.println("inside controller AddOrDeletePermission");

		boolean success = apiKeyRoleService.addOrDeletePermissionByApplicationApiKeyId(body, appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Delete and add any roles and permission  for an api key", description = "The endpoint will delete and add any  roles and permission of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PatchMapping(value = "/roles/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> AddOrDeleteRolesPermission( @PathVariable String appId,
			@PathVariable UUID apiKeyId, @Valid @RequestBody UserAddDeleteRolePermissionRequest body) {
		boolean success = apiKeyRoleService.addOrDeleteRolePermissionByApplicationApiKeyId(body, appId, apiKeyId);
		if (success) {
			return new ResponseEntity(
					new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_DELETED_ADDED)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Update the role for an api key", description = "The endpoint will Update a role of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "/roles/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> PutOrDeleteRole(
			 @PathVariable String appId,
			 @PathVariable UUID apiKeyId,
			 @Valid @RequestBody UserRoleRequest body) {
		boolean success = apiKeyRoleService.putOrDeleteRoleByApplicationApiKeyId(body, appId, apiKeyId);
		if (success) {
			return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_ADDED_TO_USER)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Update the permissions for an api key", description = "The endpoint will Update a permissions of an api key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@PutMapping(value = "/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> PutOrDeletePermission(@PathVariable String appId,
			 @PathVariable UUID apiKeyId,
			 @Valid @RequestBody UserPermissionRequest body) {
		boolean success = apiKeyRoleService.putOrDeletePermissionByApplicationApiKeyId(body, appId, apiKeyId);
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
	@PutMapping(value = "roles/permissions/applications/{appId}/apikeys/{apiKeyId}")
	public ResponseEntity<SuccessResponse> PutOrDeleteRolePermission(@PathVariable String appId,
			 @PathVariable UUID apiKeyId,
			@Valid @RequestBody UserRolePermissionRequest body) {
		boolean success = apiKeyRoleService.putOrDeleteRolesPermissionByApplicationApiKeyId(body, appId, apiKeyId);
		if (success) {
			return new ResponseEntity(
					new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_API_KEY)),
					HttpStatus.OK);

		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		}
	}

	@Operation(summary = "Get the list of application roles using application id and apiKeyId ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation") })
	@GetMapping(value = "applications/{applicationId}/apikeys/{apiKeyId}")
	public ResponseEntity<List<RoleRequest>> getRoleByApplicationUserId(
			@PathVariable String applicationId,
			@PathVariable UUID apiKeyId) {
		List<RoleRequest> role = apiKeyRoleService.getRoleByApplicationApiKeyId(applicationId, apiKeyId);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.ROLE_BY_APPLICATIONID_NOT_FOUND, applicationId));
		}
	}

}
