
package com.mids.auth.customer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mids.auth.customer.constant.StringConstant;
import com.mids.auth.customer.dto.PageResponse;
import com.mids.auth.customer.dto.SuccessResponse;
import com.mids.auth.customer.exception.ResourceNotFoundException;
import com.mids.auth.customer.request.RegisterRequest;
import com.mids.auth.customer.service.RegistrationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;




import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

public class RegisterController {

	@Autowired
	RegistrationService registerService;

	

	@ApiOperation(value = "register the customer", notes = "The endpoint will register the new customer")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful operation") })
	@PostMapping("/customers")
	public ResponseEntity<SuccessResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

		String response = registerService.registerCustomer(registerRequest);
		System.out.println(" response val " + response);
		if ("existsUserId".equalsIgnoreCase(response)) {
			return ResponseEntity.ok(new SuccessResponse(String.format(StringConstant.USER_NAME_ALREADY_TAKEN)));
		} else if ("existsEmailId".equalsIgnoreCase(response)) {
			return ResponseEntity.ok(new SuccessResponse(String.format(StringConstant.EMAIL_ALREADY_EXIST)));

		}

		return new ResponseEntity(new SuccessResponse(String.format(StringConstant.USER_REGISTER_SUCCESFULLY)),
				HttpStatus.CREATED);

	}

	@ApiOperation(value = "Get All the Customer", notes = "The endpoint will get all the customer present in auth server")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation") })
	@GetMapping("/customers")
	public ResponseEntity<PageResponse<RegisterRequest>> getAllCustomers(
			@ApiParam(value = "Page id", type = "integer", example = "0") @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@ApiParam(value = "Number of records", type = "integer", example = "10") @Positive @RequestParam(required = false, defaultValue = "10") Integer limit) {
		return ResponseEntity.ok(registerService.getAllCustomers(page, limit));
	}

	@ApiOperation(value = "Get all the deatils of prticular Customer", notes = "The endpoint will get all the deatils of a particular customer present in auth server")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation") })
	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<RegisterRequest> getCustomer(@PathVariable String id) {
		RegisterRequest customer = registerService.getCustomer(id);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.CUSTOMER_BY_ID_NOT_FOUND, id));
		}
	}

	@ApiOperation(value = "Update the prticular Customer", notes = "The endpoint will update the details of a particular customer present in auth server")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation") })
	@PutMapping(value = "/customers")
	public ResponseEntity<RegisterRequest> updateCustomer(
			@ApiParam(value = "Customer object", required = true) @Valid @RequestBody RegisterRequest updateRequest) {
		RegisterRequest customer = registerService.updateCustomer(updateRequest);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		} else {
			throw new ResourceNotFoundException(
					String.format(StringConstant.CUSTOMER_BY_ID_NOT_FOUND, updateRequest.getCustomerId()));
		}
	}

	@ApiOperation(value = "Delete the Customer", notes = "The endpoint will delete customer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation") })
	@DeleteMapping(value = "customers/{id}")
	public ResponseEntity<SuccessResponse> deleteRole(@PathVariable String id) {
		boolean success = registerService.deleteCustomer(id);
		if (success) {
			SuccessResponse successResponse = new SuccessResponse(StringConstant.CUSTOMER_IS_DELETED);
			return ResponseEntity.ok(successResponse);
		} else {
			throw new ResourceNotFoundException(String.format(StringConstant.CUSTOMER_BY_ID_NOT_FOUND, id));
		}
	}

}
