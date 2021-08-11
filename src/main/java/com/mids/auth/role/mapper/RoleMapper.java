package com.mids.auth.role.mapper;




import java.util.List;

import org.mapstruct.Mapper;

import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.request.PermissionRequest;
import com.mids.auth.role.request.RoleRequest;

@Mapper
public interface RoleMapper {
	RoleRequest roleEntityToRole(Role roleEntity);
	
	List<RoleRequest> roleEntityToRole (List<Role>  roleEntity);
    Role roleToRoleEntity(RoleRequest role);
	//Permission permissionToPermissionEntity(PermissionRequest permission);
	PermissionRequest permissionEntityToPermission(Permission permissionEntity);
	
	List<PermissionRequest> permissionEntityToPermission(List<Permission> permissionEntity);

    //Permission permissionEntityToPermission(PermissionEntity permission);

   // ModulePermission modulePermissionEntityToModulePermission(ModulePermissionEntity modulePermissionEntity);
}
