
package com.mids.auth.customer.request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mids.auth.customer.entity.EStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	 */

	@Size(max = 50)
	private String customerName;

	@Size(max = 50)
	@Email
	private String email;

	@Size(max = 50)
	private String customerId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Size(max = 150)
	private String address;

	@Size(max = 50)
	private String contactPerson;

	@Size(max = 50)
	private String contactNumber;

	@Size(max = 50)
	private String contactEmail;
	
	@Size(max = 50)
	@Enumerated(EnumType.STRING)
	private EStatus status;

	
	//private List<String> role;
	
	

}
