package com.mids.auth.role.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddOrDeletePermissionRequest {
	
	private List<UUID> addPermissions;
	private List<UUID> removePermissions;

}
