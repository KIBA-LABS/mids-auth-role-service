package com.mids.auth.role.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mids.auth.role.constant.StringConstant;
import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.exception.ResourceNotFoundException;
import com.mids.auth.role.mapper.RoleMapper;
import com.mids.auth.role.repository.PermissionRepository;
import com.mids.auth.role.repository.RoleRepository;
import com.mids.auth.role.request.PermissionRequest;
import com.mids.auth.role.request.RoleRequest;

@Service
public class PermissionServiceImpl implements PermissionService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final RoleMapper roleMapper;

	@Autowired
	public PermissionServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.roleMapper = Mappers.getMapper(RoleMapper.class);
	}

	@Override
	public String addPermission(PermissionRequest permission) {

		Role roleEntity = roleRepository.findById(permission.getRole()).orElse(null);
		if (roleEntity != null) {
			roleEntity.setId(permission.getRole());

			Permission permissionEntity = new Permission();
			permissionEntity.setPermission(permission.getPermission());
			permissionEntity.setRoles(roleEntity);
			permissionRepository.save(permissionEntity);
			return "Success";
		} else {
			return "Role";
		}
	}

	@Override
	public PageResponse<PermissionRequest> getAllPermissions(Integer pageNumber, Integer recordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, recordsPerPage);
		Page<Permission> permissionPage = permissionRepository.findAll(pageable);

		PageResponse<PermissionRequest> pageResponse = new PageResponse<>();
		pageResponse.setContent(permissionPage.getContent().stream().map(roleMapper::permissionEntityToPermission)
				.collect(Collectors.toList()));
		pageResponse.setPageNumber(pageNumber);
		pageResponse.setPageSize(permissionPage.getSize());
		pageResponse.setTotalPages(permissionPage.getTotalPages());
		pageResponse.setTotalElements(permissionPage.getTotalElements());
		pageResponse.setFirst(permissionPage.isFirst());
		pageResponse.setLast(permissionPage.isLast());
		pageResponse.setNumberOfElements(permissionPage.getNumberOfElements());
		return pageResponse;
	}

	@Override
	public PermissionRequest getPermission(UUID id) {
		Permission permissionEntity = permissionRepository.findById(id).orElse(null);
		if (permissionEntity != null) {
			return roleMapper.permissionEntityToPermission(permissionEntity);
		}
		return null;
	}
	
	
	
	
	@Override
	public PermissionRequest updatePermission(UUID id ,PermissionRequest permission) {
		
		
		
		if (permissionRepository.existsById(id)) {
            
			Role roleEntity = roleRepository.findById(permission.getRole()).orElse(null);
			if (roleEntity != null) {
				roleEntity.setId(permission.getRole());

				Permission permissionEntity = new Permission();
				permissionEntity.setPermission(permission.getPermission());
				permissionEntity.setRoles(roleEntity);
				permissionEntity.setId(id);
				permissionRepository.save(permissionEntity);
				return roleMapper.permissionEntityToPermission(permissionEntity);
			} else {
				throw new ResourceNotFoundException(String.format(StringConstant.ROLE_BY_ID_NOT_FOUND, permission.getRole()));
			}
			
		

		}

		return null;
	}
	

	@Override
	public boolean deletePermission(UUID id) {
		if (permissionRepository.existsById(id)) {

			permissionRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean deletePermissionByApplicationId(String applicationId) {
		
		if(roleRepository.existsByApplicationId(applicationId))
		{
			
			List<Permission> permissionEntity = permissionRepository.findByApplicationId(applicationId);
			
			if(!permissionEntity.isEmpty())
			{
				permissionEntity.forEach((permission)->{
					permissionRepository.deleteById(permission.getId());
				}); 
			}
			else {
				return false;
			}
					
		
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public boolean deletePermissionByApplicationRoleId(String applicationId,UUID roleId) {
		
		if(roleRepository.existsByApplicationId(applicationId))
		{
			
			List<Permission> permissionEntity = permissionRepository.findByApplicationRoleId(applicationId,roleId);
			
			if(!permissionEntity.isEmpty())
			{
				permissionEntity.forEach((permission)->{
					permissionRepository.deleteById(permission.getId());
				}); 
			}
			else {
				return false;
			}
					
		
			return true;
		}
		
		return false;
	}

	@Override
	public List<PermissionRequest> getPermissionByApplicationId(String applicationId) {
		
		List<Permission> permissionEntity = permissionRepository.findByApplicationId(applicationId);
		if (permissionEntity != null) {
			return roleMapper.permissionEntityToPermission(permissionEntity);
		}
		return null;
	}
	
	
	@Override
	public List<PermissionRequest> getPermissionByApplicationRoleId(String applicationId,UUID roleId) {
		
		List<Permission> permissionEntity = permissionRepository.findByApplicationRoleId(applicationId,roleId);
		if (permissionEntity != null) {
			return roleMapper.permissionEntityToPermission(permissionEntity);
		}
		return null;
	}

	@Override
	public List<PermissionRequest> getPermissionByApplicationUserId(String applicationId, String userId) {
		
		List<Permission> permissionEntity = permissionRepository.findByApplicationUserId(applicationId,userId);
		if (permissionEntity != null) {
			return roleMapper.permissionEntityToPermission(permissionEntity);
		}
		return null;
	}

	@Override
	public PermissionRequest getPermissionByPermissionId(UUID permissionId) {
		if(permissionRepository.existsById(permissionId))
		{
			Permission permissionEntity = permissionRepository.findById(permissionId).orElse(null);
			return roleMapper.permissionEntityToPermission(permissionEntity);
		}
		return null;
	}

	@Override
	public boolean deletePermissionByPermissionId(UUID permissionId) {
		if(permissionRepository.existsById(permissionId))
		{  
			permissionRepository.deleteById(permissionId);
			
			return true ;
		}
		return false;
	}

}
