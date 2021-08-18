package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mids.auth.role.entity.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionRequest {
	
	@NotBlank
	private List<UUID> permission;

	public List<UUID> getPermission() {
		return permission;
	}

	public void setPermission(List<UUID> permission) {
		this.permission = permission;
	}


}
