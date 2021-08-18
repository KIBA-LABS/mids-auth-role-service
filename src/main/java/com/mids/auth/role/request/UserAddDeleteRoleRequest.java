package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAddDeleteRoleRequest {
	
	private List<UUID> addRoles;
	private List<UUID> removeRoles;

}
