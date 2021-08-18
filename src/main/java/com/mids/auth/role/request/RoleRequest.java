package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mids.auth.role.entity.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
	
	@JsonProperty(access = Access.READ_ONLY)
	private UUID id;
	
	@Size(max = 200)
	private String applicationId;
	
	
	@Size(max = 50)
	@NotBlank
	private String roleName;
	
	
	private List<Permission> permissions;

}
