package com.mids.auth.role.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mids.auth.role.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
	
	@JsonProperty(access = Access.READ_ONLY)
	private UUID id;
	
	@NotBlank
	private String permission;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	private UUID role;

	
	@JsonProperty(access = Access.READ_ONLY)
	private Role roles;
}
