package com.mids.auth.role.mapper;

import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.request.PermissionRequest;
import com.mids.auth.role.request.RoleRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-10T22:22:21+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleRequest roleEntityToRole(Role roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleRequest roleRequest = new RoleRequest();

        roleRequest.setId( roleEntity.getId() );
        roleRequest.setApplicationId( roleEntity.getApplicationId() );
        roleRequest.setRoleName( roleEntity.getRoleName() );
        List<Permission> list = roleEntity.getPermissions();
        if ( list != null ) {
            roleRequest.setPermissions( new ArrayList<Permission>( list ) );
        }

        return roleRequest;
    }

    @Override
    public List<RoleRequest> roleEntityToRole(List<Role> roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        List<RoleRequest> list = new ArrayList<RoleRequest>( roleEntity.size() );
        for ( Role role : roleEntity ) {
            list.add( roleEntityToRole( role ) );
        }

        return list;
    }

    @Override
    public Role roleToRoleEntity(RoleRequest role) {
        if ( role == null ) {
            return null;
        }

        Role role1 = new Role();

        role1.setId( role.getId() );
        role1.setApplicationId( role.getApplicationId() );
        role1.setRoleName( role.getRoleName() );
        List<Permission> list = role.getPermissions();
        if ( list != null ) {
            role1.setPermissions( new ArrayList<Permission>( list ) );
        }

        return role1;
    }

    @Override
    public PermissionRequest permissionEntityToPermission(Permission permissionEntity) {
        if ( permissionEntity == null ) {
            return null;
        }

        PermissionRequest permissionRequest = new PermissionRequest();

        permissionRequest.setId( permissionEntity.getId() );
        permissionRequest.setPermission( permissionEntity.getPermission() );
        permissionRequest.setRoles( permissionEntity.getRoles() );

        return permissionRequest;
    }

    @Override
    public List<PermissionRequest> permissionEntityToPermission(List<Permission> permissionEntity) {
        if ( permissionEntity == null ) {
            return null;
        }

        List<PermissionRequest> list = new ArrayList<PermissionRequest>( permissionEntity.size() );
        for ( Permission permission : permissionEntity ) {
            list.add( permissionEntityToPermission( permission ) );
        }

        return list;
    }
}
