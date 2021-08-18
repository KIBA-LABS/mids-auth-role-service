package com.mids.auth.role.controller;


	import io.swagger.annotations.*;
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

import javax.validation.Valid;
	import javax.validation.constraints.Positive;
	import javax.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.UUID;
	import java.util.stream.Collectors;

	@Api(tags = "roles", description = "roles and permission management")
	@RestController
	@RequestMapping(value = "api/auth/roles")
	@Validated
	public class RoleController {
		
		 private final RoleService roleService;
		 
		 private final UserRoleService userRoleService;
		  @Autowired public RoleController(RoleService roleService,UserRoleService userRoleService)
		  { 
			  this.roleService = roleService; 
			  this.userRoleService = userRoleService;
		   
		  }
		 

	    @ApiOperation(value = "Get the list of application roles",
	            notes = "The endpoint will return the list of application roles")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "List of roles")
	    })
	    @GetMapping
	    public ResponseEntity<PageResponse<RoleRequest>> getAllRoles(
	            @ApiParam(value="Page id", type = "integer", example = "0")
	            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
	            @ApiParam(value="Number of records", type = "integer", example = "10")
	            @Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {
	        return ResponseEntity.ok(roleService.getAllRoles(page, limit));
	    }

	    @ApiOperation(value = "Add role",
	        notes = "The endpoint will add a role for application")
	    @ApiResponses(value = {
	            @ApiResponse(code = 201, message = "Successful operation")
	    })
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<SuccessResponse> addRole(
	            @ApiParam(value = "Role object", required = true)
	            @Valid @RequestBody RoleRequest body) {
	    	
	    	System.out.println("inside addrole controller");
	        roleService.addRole(body);
	        SuccessResponse successResponse = new SuccessResponse(StringConstant.ROLE_IS_CREATED);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    }
	    
	    
		
	    @ApiOperation(value = "Get the list of application roles using application id ",
	            notes = "The endpoint will return the list of application roles by using application id")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "List of roles by using application id")
	    })
	    @GetMapping(value = "{applicationid}")
	    public ResponseEntity<List<RoleRequest>> getRoleByApplicationId(
	            @ApiParam(value="Application id  of the object", required = true)
	            @PathVariable String applicationId) {
	    	List<RoleRequest> role = roleService.getRoleByApplicationId(applicationId);
	        if(role != null) {
	            return ResponseEntity.ok(role);
	        } else {
	            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATIONID_NOT_FOUND, applicationId));
	        }
	    }

	    @ApiOperation(value = "Update the application role",
	        notes = "The endpoint will update a role of application")
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Role object")
	    })
	    @PutMapping(value = "{id}")
	    public ResponseEntity<RoleRequest> updateRole(@ApiParam(value = "UUID of the object", required = true)
        @PathVariable UUID id,
	            @ApiParam(value = "Role object", required = true)
	            @Valid @RequestBody RoleRequest body) {
	        RoleRequest role = roleService.updateRole(id,body);
	        if(role != null) {
	            return ResponseEntity.ok(role);
	        }else {
	            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, body.getId()));
	        }
	    }

	    @ApiOperation(value = "Delete the application role",
	        notes = "The endpoint will delete a role of any application")
	    @ApiResponses(value = {
	            @ApiResponse(code = 204, message = "Successful operation")
	    })
	    @DeleteMapping(value = "{applicationId}")
	    public ResponseEntity<SuccessResponse> deleteRole(
	            @ApiParam(value="UUID of the object", required = true)
	            @PathVariable String applicationId) {
	        boolean success = roleService.deleteRoleByApplicationId(applicationId);
	        if(success) {
	        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
						HttpStatus.NO_CONTENT);
	            
	            
	        } else {
	            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, applicationId));
	        }
	    }
	    
	    
	    @ApiOperation(value = "Delete the application role",
		        notes = "The endpoint will delete a role of any application")
		    @ApiResponses(value = {
		            @ApiResponse(code = 204, message = "Successful operation")
		    })
		    @DeleteMapping(value = "{applicationId}/{id}")
		    public ResponseEntity<SuccessResponse> deleteRole(
		            @ApiParam(value="ApplcationId of the object", required = true)
		            @PathVariable String applicationId,@ApiParam(value="UUID of the object", required = true)
		            @PathVariable UUID id) {
		        boolean success = roleService.deleteRoleByApplicationRoleId(applicationId,id);
		        if(success) {
		        	return new ResponseEntity(new SuccessResponse(String.format(StringConstant.ROLE_IS_DELETED)),
							HttpStatus.NO_CONTENT);
		        } else {
		            throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_APPLICATION_ID_NOT_FOUND, applicationId));
		        }
		    }
	    
	    



	    
	}


