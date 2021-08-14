package com.mids.auth.role.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.SuccessResponse;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.request.UserRoleRequest;
import com.mids.auth.role.service.UserRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "userroles", description = "roles and user management")
@RestController
@RequestMapping(value = "new/main/applications")
@Validated
public class UserRoleController {

	private final UserRoleService userRoleService;

	@Autowired
	public UserRoleController(UserRoleService userRoleService) {

		this.userRoleService = userRoleService;

	}

	@ApiOperation(value = "Add roles to any user", notes = "The endpoint will add a role to any user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping("/{​​​​​​​appId}​​​​​​​/users/{​​​​​​​userId}​​​​​​​")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse> addUserRole(
			@ApiParam(value = "Application of the user", required = true)
	@PathVariable("appId") String appId,
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

}
