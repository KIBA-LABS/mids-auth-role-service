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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "userroles", description = "roles and user management")
@RestController
@RequestMapping(value = "api/auth")
@Validated
public class UserRoleController {

	private final UserRoleService userRoleService;

	@Autowired
	public UserRoleController(UserRoleService userRoleService) {

		this.userRoleService = userRoleService;

	}

	@ApiOperation(value = "Add roles to any user", notes = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping("/roles/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRole(
			@ApiParam(value = "Application of the user", required = true)
	@PathVariable(value="appId") String appId,
	@ApiParam(value = "userId of the user", required = true)
	@PathVariable(value = "userId") String userId,
			@ApiParam(value = "UserRole object", required = true) 
	@Valid @RequestBody UserRoleRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");
		
		String response = userRoleService.addUserRole(body,appId,userId);
		
		
			successResponse = new SuccessResponse(StringConstant.ROLE_IS_ADDED_TO_USER);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}
	
	@ApiOperation(value = "Add roles to any user", notes = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping("/permissions/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserPermision(
			@ApiParam(value = "Application of the user", required = true)
	@PathVariable(value="appId") String appId,
	@ApiParam(value = "userId of the user", required = true)
	@PathVariable(value = "userId") String userId,
			@ApiParam(value = "UserRole object", required = true) 
	@Valid @RequestBody UserPermissionRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");
		
		String response = userRoleService.addUserPermission(body,appId,userId);
		
		
			successResponse = new SuccessResponse(StringConstant.PERMISSION_IS_ADDED_TO_USER);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}
	
	@ApiOperation(value = "Add roles to any user", notes = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping("roles/permissions/applications/{appId}/users/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRolePermision(
			@ApiParam(value = "Application of the user", required = true)
	@PathVariable(value="appId") String appId,
	@ApiParam(value = "userId of the user", required = true)
	@PathVariable(value = "userId") String userId,
			@ApiParam(value = "UserRole object", required = true) 
	@Valid @RequestBody UserRolePermissionRequest body) {
		SuccessResponse successResponse;
		System.out.println("inside UserRoleRequest controller");
		
		String response = userRoleService.addUserRolePermission(body,appId,userId);
		
		
			successResponse = new SuccessResponse(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_USER);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}
	
	 @ApiOperation(value = "Delete the role for any user",
		        notes = "The endpoint will delete a role of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @DeleteMapping(value = "/roles/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> deleteRole(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId) {
		        boolean success = userRoleService.deleteRoleByApplicationUserId(appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
							HttpStatus.NO_CONTENT);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 @ApiOperation(value = "Delete the permissions for any user",
		        notes = "The endpoint will delete a permissions of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @DeleteMapping(value = "/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> deletePermission(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId) {
		        boolean success = userRoleService.deleteRoleByApplicationUserId(appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED)),
							HttpStatus.NO_CONTENT);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 
	 @ApiOperation(value = "Delete the role for any user",
		        notes = "The endpoint will delete a role of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @DeleteMapping(value = "/roles/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> deleteRoleAndPermissions(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId) {
		        boolean success = userRoleService.deleteRoleByApplicationUserId(appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
							HttpStatus.NO_CONTENT);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 
	 @ApiOperation(value = "Delete the role for any user",
		        notes = "The endpoint will delete a role of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
	 		@PatchMapping(value = "/roles/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> AddOrDeleteRole(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserAddDeleteRoleRequest body) {
		        boolean success = userRoleService.addOrDeleteRoleByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED_ADDED)),
							HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 
	 
	 @ApiOperation(value = "Delete the permissions for any user",
		        notes = "The endpoint will delete a permissions of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @PatchMapping(value = "/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> AddOrDeletePermission(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserAddOrDeletePermissionRequest body) {
		 System.out.println("inside controller AddOrDeletePermission");
		 
		        boolean success = userRoleService.addOrDeletePermissionByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_DELETED_ADDED)),
							HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 @ApiOperation(value = "Delete the role for any user",
		        notes = "The endpoint will delete a role of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
	 		@PatchMapping(value = "/roles/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> AddOrDeleteRolesPermission(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserAddDeleteRolePermissionRequest body) {
		        boolean success = userRoleService.addOrDeleteRolePermissionByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_DELETED_ADDED)),
							HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 @ApiOperation(value = "Delete the role for any user",
		        notes = "The endpoint will delete a role of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
	 		@PutMapping(value = "/roles/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> PutOrDeleteRole(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserRoleRequest body) {
		        boolean success = userRoleService.putOrDeleteRoleByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_ADDED_TO_USER)),
		        			HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 
	 @ApiOperation(value = "Delete the permissions for any user",
		        notes = "The endpoint will delete a permissions of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @PutMapping(value = "/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> PutOrDeletePermission(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserPermissionRequest body) {
		        boolean success = userRoleService.putOrDeletePermissionByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.PERMISSION_IS_ADDED_TO_USER)),
							HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
	 
	 
	 @ApiOperation(value = "Delete the permissions for any user",
		        notes = "The endpoint will delete a permissions of any user")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @PutMapping(value = "roles/permissions/applications/{appId}/users/{userId}")
		    public ResponseEntity<SuccessResponse> PutOrDeleteRolePermission(
		            @ApiParam(value="applicationId of the object", required = true)
		            @PathVariable String appId,@ApiParam(value="userId of the object", required = true)
		            @PathVariable String userId,@ApiParam(value = "UserRole object", required = true) 
		        	@Valid @RequestBody UserRolePermissionRequest body) {
		        boolean success = userRoleService.putOrDeleteRolesPermissionByApplicationUserId(body,appId,userId);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLES_PERMISSION_IS_ADDED_TO_USER)),
							HttpStatus.OK);
		            
		            
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.PERMISSION_BY_APPLICATION_ID_NOT_FOUND, appId));
		        }
		    }
		    
		    

}
