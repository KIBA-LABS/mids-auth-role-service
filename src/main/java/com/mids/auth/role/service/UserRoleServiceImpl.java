package com.mids.auth.role.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.entity.PermissionEmbeddedKey;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.entity.RoleEmbeddedKey;
import com.mids.auth.role.entity.UserPermission;
import com.mids.auth.role.entity.UserRole;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.mapper.RoleMapper;
import com.mids.auth.role.repository.PermissionRepository;
import com.mids.auth.role.repository.RoleRepository;
import com.mids.auth.role.repository.UserPermissionRepository;
import com.mids.auth.role.repository.UserRoleRepository;
import com.mids.auth.role.request.UserAddDeleteRolePermissionRequest;
import com.mids.auth.role.request.UserAddDeleteRoleRequest;
import com.mids.auth.role.request.UserAddOrDeletePermissionRequest;
import com.mids.auth.role.request.UserPermissionRequest;
import com.mids.auth.role.request.UserRolePermissionRequest;
import com.mids.auth.role.request.UserRoleRequest;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserPermissionRepository userPermission;
	private final RoleMapper roleMapper;

	@Autowired
	public UserRoleServiceImpl(RoleRepository roleRepository, UserRoleRepository userRoleRepository,
			PermissionRepository permissionRepository, UserPermissionRepository userPermission) {
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.permissionRepository = permissionRepository;
		this.userPermission = userPermission;
		this.roleMapper = Mappers.getMapper(RoleMapper.class);
	}

	@Override
	public String addUserRole(UserRoleRequest userRoleRequest, String applicationId, String userId) {
		List<UUID> roles = userRoleRequest.getRole();
        if(validateRole(roles,applicationId,userId))
        {
		addRole(roles, applicationId, userId);
        }
		return "Success";

	}

	@Override
	public String addUserPermission(UserPermissionRequest body, String appId, String userId) {
		List<UUID> permissions = body.getPermission();
		if(validatePermission(permissions,appId,userId))
        {
		addPermission(permissions, appId, userId);
        }

		return "Success";
	}

	@Override
	public String addUserRolePermission(@Valid UserRolePermissionRequest body, String appId, String userId) {
		List<UUID> roles = body.getRole();
		List<UUID> permissions = body.getPermission();

		boolean validRole= validateRole(roles,appId,userId);
		boolean validPermission= validatePermission(permissions,appId,userId);
	        
		System.out.println("validRole "+ validRole  + "  validPermission      "+  validPermission);
		if(validRole && validPermission)
		{
		addRole(roles, appId, userId);

		addPermission(permissions, appId, userId);
		}

		return "Success";
	}

	@Override
	public boolean deleteRoleByApplicationUserId(String applicationId, String userId) {
		// TODO Auto-generated method stub
		System.out.println("inside service deleteRoleByApplicationUserId");
		List<UUID> roles = userRoleRepository.getRoleIdByApplicationUserId(Integer.parseInt(applicationId), userId);
		if(!roles.isEmpty())
		{
		boolean response= deleteRole(roles, applicationId,userId);
		}
		else {
			return false;
		}
			return true;
	}

	@Override
	public boolean deletePermissionByApplicationUserId(String applicationId, String userId) {

		List<UUID> roles = userRoleRepository.getRoleIdByApplicationUserId(Integer.parseInt(applicationId), userId);
		List<UserPermission> userPermissionEntity = new ArrayList();
		if (!roles.isEmpty()) {
			roles.forEach(role -> {

				List<UUID> permissions = userPermission.getPermissionIdByApplicationUserId(role, userId);

				if (!permissions.isEmpty()) {
					deletePermission(permissions, applicationId,userId);

				}
			});

			
			
	}
		
		else {
			return false;
		}
		return true;
	}

	@Override
	public boolean addOrDeleteRoleByApplicationUserId(UserAddDeleteRoleRequest body, String appId, String userId) {
		
		
		
		List<UUID> removeRoles = body.getRemoveRoles();
		List<UUID> addRoles = body.getAddRoles();
		//addRoles.removeAll(removeRoles);
		boolean validRole= validateRole(addRoles,appId,userId);
		if(validRole)
		{
		deleteRole(removeRoles, appId, userId);
		addRole(addRoles, appId, userId);
		}

		return true;
	}

	@Override
	public boolean addOrDeletePermissionByApplicationUserId(UserAddOrDeletePermissionRequest body, String appId,
			String userId) {
		System.out.println("inside addOrDeletePermissionByApplicationUserId");
		List<UUID> removePermissions = body.getRemovePermissions();
		List<UUID> addPermissions = body.getAddPermissions();
		
		boolean validPermission= validatePermission(addPermissions,appId,userId);
		if(validPermission)
		{
			System.out.println("inside addOrDeletePermissionByApplicationUserId validPermissions ");
		deletePermission(removePermissions, appId, userId);
		addPermission(addPermissions, appId, userId);
		}
		return true;
	}
	
	
	@Override
	public boolean putOrDeleteRoleByApplicationUserId(UserRoleRequest body, String appId, String userId) {
		List<UUID> addRoles = body.getRole();
		boolean validRole= validateRole(addRoles,appId,userId);
		if(validRole)
		{
			List<UUID> roles = userRoleRepository.getRoleIdByApplicationUserId(Integer.parseInt(appId), userId);
		
		if(!roles.isEmpty())
		{
		 deleteRole(roles, appId,userId);
		}
		
	    
		addRole(addRoles, appId, userId);
	}
		
		return true;
	}

	@Override
	public boolean putOrDeletePermissionByApplicationUserId(UserPermissionRequest body, String appId, String userId) {
		List<UUID> addPermissions = body.getPermission();
		boolean validPermission= validatePermission(addPermissions,appId,userId);
		if(validPermission)
		{
		List<UUID> roles = userRoleRepository.getRoleIdByApplicationUserId(Integer.parseInt(appId), userId);
		List<UserPermission> userPermissionEntity = new ArrayList();
		if (!roles.isEmpty()) {
			roles.forEach(role -> {

				List<UUID> permissions = userPermission.getPermissionIdByApplicationUserId(role, userId);

				if (!permissions.isEmpty()) {
					deletePermission(permissions, appId,userId);

				}
			});

			
			
	}
		

		addPermission(addPermissions, appId, userId);
		}
		
		return true;
	}

	
	@Override
	public boolean putOrDeleteRolesPermissionByApplicationUserId( UserRolePermissionRequest body, String appId,
			String userId) {
		List<UUID> roles = body.getRole();
		List<UUID> permissions = body.getPermission();
		boolean validRole= validateRole(roles,appId,userId);
		boolean validPermission= validatePermission(permissions,appId,userId);
		
		if(validRole && validPermission)
		{
		List<UUID> removeRoles = userRoleRepository.getRoleIdByApplicationUserId(Integer.parseInt(appId), userId);
		List<UserPermission> userPermissionEntity = new ArrayList();
		if (!removeRoles.isEmpty()) {
			removeRoles.forEach(role -> {

				List<UUID> removePermissions = userPermission.getPermissionIdByApplicationUserId(role, userId);

				if (!permissions.isEmpty()) {
					deletePermission(removePermissions, appId,userId);

				}
			});

			deleteRole(removeRoles, appId,userId);	
			
	}
		
		addRole(roles, appId, userId);

		addPermission(permissions, appId, userId);
		}

		return true;
	}
	

	public void addRole(List<UUID> roles, String appId, String userId)

	{

		List<UserRole> userRoleEnitity = new ArrayList();
		roles.forEach((role) -> {
			RoleEmbeddedKey key = new RoleEmbeddedKey(userId, role);
			if (roleRepository.existsByRoleAndApplicationId(role, Integer.parseInt(appId))) {
				System.out.println("inside add role repo");
				UserRole usersingleRoleEntity = new UserRole();
				usersingleRoleEntity.setKey(key);
				userRoleEnitity.add(usersingleRoleEntity);
			}
		});
        if(userRoleEnitity!=null)
        {
		userRoleRepository.saveAll(userRoleEnitity);
        }

		
	}

	public boolean deleteRole(List<UUID> roles, String appId, String userId)

	{

		List<UserRole> userRoleEntity = new ArrayList();
		List<UserPermission> userPermissionEntity = new ArrayList();
		if (!roles.isEmpty()) {
			roles.forEach(role -> {
				RoleEmbeddedKey key = new RoleEmbeddedKey(userId, role);
				if(userRoleRepository.existsByKey(key))
				{
				userRoleEntity.add(userRoleRepository.findByKey(key));
				List<UUID> permissions = userPermission.getPermissionIdByApplicationUserId(role, userId);

				if (!permissions.isEmpty()) {
					permissions.forEach(permission -> {

						PermissionEmbeddedKey perimissionKey = new PermissionEmbeddedKey(userId, permission);
						if(userPermission.existsByKey(perimissionKey))
						userPermissionEntity.add(userPermission.findByKey(perimissionKey));

					});

				}
				}
			});
			userPermission.deleteAll(userPermissionEntity);
			userRoleRepository.deleteAll(userRoleEntity);

		}
		
		else {
			return false;
		}
		return true;
	}

	public void addPermission(List<UUID> permissions, String appId, String userId) {
		List<UserPermission> userPermissionEnitity = new ArrayList();
		permissions.forEach((permission) -> {

			PermissionEmbeddedKey key = new PermissionEmbeddedKey(userId, permission);
			if (permissionRepository.existsByPermissionRoleAndApplicationId(permission, Integer.parseInt(appId))) {
				UserPermission userSinglePermissionEntity = new UserPermission();

				userSinglePermissionEntity.setKey(key);
				userPermissionEnitity.add(userSinglePermissionEntity);

			}
		});

		userPermission.saveAll(userPermissionEnitity);

	}
	
	
	public void deletePermission(List<UUID> permissions, String appId, String userId)
	{
		
		List<UserPermission> userPermissionEntity = new ArrayList();

		if (!permissions.isEmpty()) {
			permissions.forEach(permission -> {

				PermissionEmbeddedKey perimissionKey = new PermissionEmbeddedKey(userId, permission);
				if(userPermission.existsByKey(perimissionKey))
				userPermissionEntity.add(userPermission.findByKey(perimissionKey));

			});

		
		}
		userPermission.deleteAll(userPermissionEntity);
	}

	public boolean validatePermission(List<UUID> permissions, String appId, String userId)
	{
		
		permissions.forEach((permission) -> {
			
			if(!permissionRepository.existsByPermissionRoleAndApplicationId(permission, Integer.parseInt(appId)))
			{
				throw new ResourceNotFoundException(
						String.format("Permission with uuid " + permission + " is not present in the system for given application"));
			}
			PermissionEmbeddedKey key = new PermissionEmbeddedKey(userId, permission);
			if (userPermission.existsByKey(key)) {
				throw new ResourceNotFoundException(
						String.format("Permission " + permission + " is already attached to " + userId));

			}

		});
		
		return true;
	}
	
	public boolean validateRole(List<UUID> roles, String appId, String userId)
	{
		
		roles.forEach((role) -> {
			if(!roleRepository.existsByRoleAndApplicationId(role,Integer.parseInt(appId)))
			{
				throw new ResourceNotFoundException(
						String.format("Role with uuid " + role + " is not present in the system for given application"));
			}
			
			RoleEmbeddedKey key = new RoleEmbeddedKey(userId, role);
			if (userRoleRepository.existsByKey(key)) {
				throw new ResourceNotFoundException(
						String.format("Role " + role + " is already attached to " + userId));

			}

		});
		
		return true;
	}

	@Override
	public boolean addOrDeleteRolePermissionByApplicationUserId(UserAddDeleteRolePermissionRequest body,
			String appId, String userId) {
		List<UUID> addRoles = body.getAddRoles();
		List<UUID> addPermissions = body.getAddPermissions();
		boolean validRole= validateRole(addRoles,appId,userId);
		boolean validPermission= validatePermission(addPermissions,appId,userId);
		if(validRole && validPermission)
		{
			List<UUID> removeRoles = body.getRemoveRoles();
			List<UUID> inputRemoveRoles = body.getRemovePermissions();
			deletePermission(inputRemoveRoles, appId,userId);
			List<UserPermission> userPermissionEntity = new ArrayList();
			if (!removeRoles.isEmpty()) {
				removeRoles.forEach(role -> {

					List<UUID> removePermissions = userPermission.getPermissionIdByApplicationUserId(role, userId);

					if (!removePermissions.isEmpty()) {
						deletePermission(removePermissions, appId,userId);

					}
				});

				deleteRole(removeRoles, appId,userId);	
		}
			
			addRole(addRoles, appId, userId);

			addPermission(addPermissions, appId, userId);
		return true;
	}
		return true;
	}

	
}
