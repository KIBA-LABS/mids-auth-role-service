package com.mids.auth.role.service;

import com.mids.auth.role.entity.*;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.mapper.RoleMapper;
import com.mids.auth.role.repository.*;
import com.mids.auth.role.request.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyRoleServiceImpl implements ApiKeyRoleService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final ApiKeyRoleRepository apiKeyRoleRepository;
	private final ApiKeyPermissionRepository apiKeyPermissionRepository;
	private final RoleMapper roleMapper;

	@Autowired
	public ApiKeyRoleServiceImpl(RoleRepository roleRepository, ApiKeyRoleRepository apiKeyRoleRepository,
                                 PermissionRepository permissionRepository, ApiKeyPermissionRepository apiKeyPermissionRepository) {
		this.roleRepository = roleRepository;
		this.apiKeyRoleRepository = apiKeyRoleRepository;
		this.permissionRepository = permissionRepository;
		this.apiKeyPermissionRepository = apiKeyPermissionRepository;
		this.roleMapper = Mappers.getMapper(RoleMapper.class);
	}

	@Override
	public String addApiKeyRole(UserRoleRequest userRoleRequest, String applicationId, UUID apiKeyId) {
		List<UUID> roles = userRoleRequest.getRole();
        if(validateRole(roles,applicationId,apiKeyId))
        {
			addRole(roles, applicationId, apiKeyId);
        }
		return "Success";

	}

	@Override
	public String addApiKeyPermission(UserPermissionRequest body, String appId, UUID apiKeyId) {
		List<UUID> permissions = body.getPermission();
		if(validatePermission(permissions,appId,apiKeyId))
        {
			addPermission(permissions, appId, apiKeyId);
        }

		return "Success";
	}

	@Override
	public List<RoleRequest> getRoleByApplicationApiKeyId(String applicationId, UUID apiKeyId) {
		if (apiKeyRoleRepository.existsByApplicationApiKeyId(applicationId, apiKeyId)) {

			List<Role> roleEntity = apiKeyRoleRepository.findByApplicationApiKeyId(applicationId,apiKeyId);

			if(roleEntity!=null)
				return roleMapper.roleEntityToRole(roleEntity);
		}
		return null;
	}



        @Override
        public String addApiKeyRolePermission(@Valid UserRolePermissionRequest body, String appId, UUID apiKeyId) {
            List<UUID> roles = body.getRole();
            List<UUID> permissions = body.getPermission();

            boolean validRole= validateRole(roles,appId,apiKeyId);


            System.out.println("validRole "+ validRole  + "  validPermission      ");
            if(validRole)
            {
            addRole(roles, appId, apiKeyId);
            boolean validPermission= validatePermission(permissions,appId,apiKeyId);
            if(validPermission)
            addPermission(permissions, appId, apiKeyId);
            }

            return "Success";
        }

        @Override
        public boolean deleteRoleByApplicationApiKeyId(String applicationId, UUID apiKeyId) {
            // TODO Auto-generated method stub
            System.out.println("inside service deleteRoleByApplicationUserId");
            List<UUID> roles = apiKeyRoleRepository.getRoleIdByApiKeyId(applicationId, apiKeyId);
            if(!roles.isEmpty())
            {
            boolean response= deleteRole(roles, applicationId,apiKeyId);
            }
            else {
                return false;
            }
                return true;
        }

        @Override
        public boolean deletePermissionByApplicationApiKeyId(String applicationId, UUID apiKeyId) {

            List<UUID> roles = apiKeyRoleRepository.getRoleIdByApiKeyId(applicationId, apiKeyId);
            List<UserPermission> userPermissionEntity = new ArrayList();
            if (!roles.isEmpty()) {
                roles.forEach(role -> {

                    List<UUID> permissions = apiKeyPermissionRepository.getPermissionIdByApplicationApiKeyId(role, apiKeyId);

                    if (!permissions.isEmpty()) {
                        deletePermission(permissions, applicationId,apiKeyId);

                    }
                });



        }

            else {
                return false;
            }
            return true;
        }

        @Override
        public boolean addOrDeleteRoleByApplicationApiKeyId(UserAddDeleteRoleRequest body, String appId, UUID apiKeyId) {



            List<UUID> removeRoles = body.getRemoveRoles();
            List<UUID> addRoles = body.getAddRoles();
            //addRoles.removeAll(removeRoles);
            boolean validRole= validateRole(addRoles,appId,apiKeyId);
            if(validRole)
            {
            deleteRole(removeRoles, appId, apiKeyId);
            addRole(addRoles, appId, apiKeyId);
            }

            return true;
        }

        @Override
        public boolean addOrDeletePermissionByApplicationApiKeyId(UserAddOrDeletePermissionRequest body, String appId,
                UUID apiKeyId) {
            System.out.println("inside addOrDeletePermissionByApplicationUserId");
            List<UUID> removePermissions = body.getRemovePermissions();
            List<UUID> addPermissions = body.getAddPermissions();

            boolean validPermission= validatePermission(addPermissions,appId,apiKeyId);
            if(validPermission)
            {
                System.out.println("inside addOrDeletePermissionByApplicationUserId validPermissions ");
            deletePermission(removePermissions, appId, apiKeyId);
            addPermission(addPermissions, appId, apiKeyId);
            }
            return true;
        }


        @Override
        public boolean putOrDeleteRoleByApplicationApiKeyId(UserRoleRequest body, String appId, UUID apiKeyId) {
            List<UUID> addRoles = body.getRole();
            boolean validRole= validateRole(addRoles,appId,apiKeyId);
            if(validRole)
            {
                List<UUID> roles = apiKeyRoleRepository.getRoleIdByApiKeyId(appId, apiKeyId);

            if(!roles.isEmpty())
            {
             deleteRole(roles, appId,apiKeyId);
            }


            addRole(addRoles, appId, apiKeyId);
        }

            return true;
        }

        @Override
        public boolean putOrDeletePermissionByApplicationApiKeyId(UserPermissionRequest body, String appId, UUID apiKeyId) {
            List<UUID> addPermissions = body.getPermission();
            boolean validPermission= validatePermission(addPermissions,appId,apiKeyId);
            if(validPermission)
            {
            List<UUID> roles = apiKeyRoleRepository.getRoleIdByApiKeyId(appId, apiKeyId);
            List<UserPermission> userPermissionEntity = new ArrayList();
            if (!roles.isEmpty()) {
                roles.forEach(role -> {

                    List<UUID> permissions = apiKeyPermissionRepository.getPermissionIdByApplicationApiKeyId(role, apiKeyId);

                    if (!permissions.isEmpty()) {
                        deletePermission(permissions, appId,apiKeyId);

                    }
                });



        }


            addPermission(addPermissions, appId, apiKeyId);
            }

            return true;
        }


        @Override
        public boolean putOrDeleteRolesPermissionByApplicationApiKeyId( UserRolePermissionRequest body, String appId,
                UUID apiKeyId) {
            List<UUID> roles = body.getRole();
            List<UUID> permissions = body.getPermission();
            boolean validRole= validateRole(roles,appId,apiKeyId);


            if(validRole)
            {
            List<UUID> removeRoles = apiKeyRoleRepository.getRoleIdByApiKeyId(appId, apiKeyId);
            List<UserPermission> userPermissionEntity = new ArrayList();
            if (!removeRoles.isEmpty()) {
                removeRoles.forEach(role -> {

                    List<UUID> removePermissions = apiKeyPermissionRepository.getPermissionIdByApplicationApiKeyId(role, apiKeyId);

                    if (!permissions.isEmpty()) {
                        deletePermission(removePermissions, appId,apiKeyId);

                    }
                });

                deleteRole(removeRoles, appId,apiKeyId);

        }

            addRole(roles, appId, apiKeyId);
            boolean validPermission= validatePermission(permissions,appId,apiKeyId);
            if(validPermission)
            addPermission(permissions, appId, apiKeyId);
            }

            return true;
        }

	public void addRole(List<UUID> roles, String appId, UUID apiKeyId)

	{

		List<ApiKeyRole> apiKeyRoleEnitity = new ArrayList();
		roles.forEach((role) -> {
			ApiKeyRoleEmbeddedKey key = new ApiKeyRoleEmbeddedKey(apiKeyId, role);
			if (roleRepository.existsByRoleAndApplicationId(role, appId)) {
				System.out.println("inside add role repo");
				ApiKeyRole apiKeyRole = new ApiKeyRole();
				apiKeyRole.setKey(key);
				apiKeyRoleEnitity.add(apiKeyRole);
			}
		});
        if(apiKeyRoleEnitity!=null)
        {
			apiKeyRoleRepository.saveAll(apiKeyRoleEnitity);
        }

		
	}

	public boolean deleteRole(List<UUID> roles, String appId, UUID apiKeyId)
	{
		List<ApiKeyRole> apiKeyRoleEntity = new ArrayList();
		List<ApiKeyPermission> apiKeyPermissionEntity = new ArrayList();
		if (!roles.isEmpty()) {
			roles.forEach(role -> {
				ApiKeyRoleEmbeddedKey key = new ApiKeyRoleEmbeddedKey(apiKeyId, role);
				if(apiKeyRoleRepository.existsByKey(key))
				{
					apiKeyRoleEntity.add(apiKeyRoleRepository.findByKey(key));
				List<UUID> permissions = apiKeyPermissionRepository.getPermissionIdByApplicationApiKeyId(role, apiKeyId);

				if (!permissions.isEmpty()) {
					permissions.forEach(permission -> {

						ApiKeyPermissionEmbeddedKey perimissionKey = new ApiKeyPermissionEmbeddedKey(apiKeyId, permission);
						if(apiKeyPermissionRepository.existsByKey(perimissionKey))
							apiKeyPermissionEntity.add(apiKeyPermissionRepository.findByKey(perimissionKey));

					});
				}
				}
			});
			apiKeyPermissionRepository.deleteAll(apiKeyPermissionEntity);
			apiKeyRoleRepository.deleteAll(apiKeyRoleEntity);
		}
		
		else {
			return false;
		}
		return true;
	}

	public void addPermission(List<UUID> permissions, String appId, UUID apiKeyId) {
		List<ApiKeyPermission> apiKeyPermissionEnitity = new ArrayList();
		permissions.forEach((permission) -> {

			ApiKeyPermissionEmbeddedKey key = new ApiKeyPermissionEmbeddedKey(apiKeyId, permission);
			if (permissionRepository.existsByPermissionRoleAndApplicationId(permission, appId)) {
				ApiKeyPermission apiKeyPermission = new ApiKeyPermission();

				apiKeyPermission.setKey(key);
				apiKeyPermissionEnitity.add(apiKeyPermission);

			}
		});

		apiKeyPermissionRepository.saveAll(apiKeyPermissionEnitity);

	}
	

	public void deletePermission(List<UUID> permissions, String appId, UUID apiKeyId)
	{
		List<ApiKeyPermission> apiKeyPermissionEntity = new ArrayList();

		if (!permissions.isEmpty()) {
			permissions.forEach(permission -> {

				ApiKeyPermissionEmbeddedKey perimissionKey = new ApiKeyPermissionEmbeddedKey(apiKeyId, permission);
				if(apiKeyPermissionRepository.existsByKey(perimissionKey))
					apiKeyPermissionEntity.add(apiKeyPermissionRepository.findByKey(perimissionKey));

			});
		}
		apiKeyPermissionRepository.deleteAll(apiKeyPermissionEntity);
	}

	public boolean validatePermission(List<UUID> permissions, String appId, UUID apiKeyId)
	{
		permissions.forEach((permission) -> {
			
			if(!permissionRepository.existsByPermissionRoleAndApplicationId(permission, appId))
			{
				throw new ResourceNotFoundException(
						String.format("Permission with uuid " + permission + " is not present in the system for given application"));
			}
			
			UUID role = permissionRepository.findByPermissionApplicationId(permission, appId);
			ApiKeyRoleEmbeddedKey roleKey = new ApiKeyRoleEmbeddedKey(apiKeyId, role);
			if(!apiKeyRoleRepository.existsByKey(roleKey))
			{
				throw new ResourceNotFoundException(
						String.format("Role with id "+role +" attached to Permission with uuid " + permission + " is not attached in the system for given api key"));
			}
			
			ApiKeyPermissionEmbeddedKey key = new ApiKeyPermissionEmbeddedKey(apiKeyId, permission);
			if (apiKeyPermissionRepository.existsByKey(key)) {
				throw new ResourceNotFoundException(
						String.format("Permission " + permission + " is already attached to " + apiKeyId));
			}
		});
		return true;
	}


	public boolean validateRole(List<UUID> roles, String appId, UUID apiKeyId)
	{
		roles.forEach((role) -> {
			if(!roleRepository.existsByRoleAndApplicationId(role,appId))
			{
				throw new ResourceNotFoundException(
						String.format("Role with uuid " + role + " is not present in the system for given application"));
			}
			ApiKeyRoleEmbeddedKey key = new ApiKeyRoleEmbeddedKey(apiKeyId, role);
			if (apiKeyRoleRepository.existsByKey(key)) {
				throw new ResourceNotFoundException(
						String.format("Role " + role + " is already attached to " + apiKeyId));
			}
		});
		return true;
	}

	@Override
	public boolean addOrDeleteRolePermissionByApplicationApiKeyId(UserAddDeleteRolePermissionRequest body,
			String appId, UUID apiKeyId) {
		List<UUID> addRoles = body.getAddRoles();
		List<UUID> addPermissions = body.getAddPermissions();
		boolean validRole= validateRole(addRoles,appId,apiKeyId);
		if(validRole)
		{
			List<UUID> removeRoles = body.getRemoveRoles();
			List<UUID> inputRemoveRoles = body.getRemovePermissions();
			deletePermission(inputRemoveRoles, appId,apiKeyId);
			List<UserPermission> userPermissionEntity = new ArrayList();
			if (!removeRoles.isEmpty()) {
				removeRoles.forEach(role -> {
					List<UUID> removePermissions = apiKeyPermissionRepository.getPermissionIdByApplicationApiKeyId(role, apiKeyId);
					if (!removePermissions.isEmpty()) {
						deletePermission(removePermissions, appId,apiKeyId);

					}
				});

				deleteRole(removeRoles, appId,apiKeyId);
		}
			
			addRole(addRoles, appId, apiKeyId);
			boolean validPermission= validatePermission(addPermissions,appId,apiKeyId);
			if(validPermission)
			addPermission(addPermissions, appId, apiKeyId);
		return true;
	}
		return true;
	}

	
}
