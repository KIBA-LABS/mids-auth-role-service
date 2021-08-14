package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mids.auth.role.entity.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequest {
	
	
	@NotBlank
	private List<UUID> role;

	public List<UUID> getRole() {
		return role;
	}

	public void setRole(List<UUID> role) {
		this.role = role;
	}

	

}
