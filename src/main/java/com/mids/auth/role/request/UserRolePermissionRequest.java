package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePermissionRequest {
	
	@NotBlank
	private List<UUID> role;
	
	@NotBlank
	private List<UUID> permission;

}
