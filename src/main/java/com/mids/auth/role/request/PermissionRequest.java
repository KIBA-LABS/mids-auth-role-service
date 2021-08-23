package com.mids.auth.role.request;

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
public class PermissionRequest {
	
	
	private UUID id;
	
	@Size(max = 50)
	@NotBlank
	private String permission;
	
	@NotBlank
	private UUID role;

	
	
	private Role roles;

	@JsonProperty
	public UUID getId() {
		return id;
	}

	@JsonIgnore
	public void setId(UUID id) {
		this.id = id;
	}


	public String getPermission() {
		return permission;
	}


	public void setPermission(String permission) {
		this.permission = permission;
	}

	@JsonIgnore
	public UUID getRole() {
		return role;
	}

	@JsonProperty
	public void setRole(UUID role) {
		this.role = role;
	}

	@JsonProperty
	public Role getRoles() {
		return roles;
	}

	@JsonIgnore
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
	
	
}
