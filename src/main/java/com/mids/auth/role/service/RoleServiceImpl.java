package com.mids.auth.role.service;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mids.auth.role.dto.PageResponse;
import com.mids.auth.role.entity.EPermission;
import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.Role;
import com.mids.auth.role.mapper.RoleMapper;
import com.mids.auth.role.repository.PermissionRepository;
import com.mids.auth.role.repository.RoleRepository;
import com.mids.auth.role.request.RoleRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final RoleMapper roleMapper;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.roleMapper = Mappers.getMapper(RoleMapper.class);
	}

	@Override
	public PageResponse<RoleRequest> getAllRoles(Integer pageNumber, Integer recordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, recordsPerPage);
		Page<Role> rolePage = roleRepository.findAll(pageable);

		PageResponse<RoleRequest> pageResponse = new PageResponse<>();
		pageResponse.setContent(
				rolePage.getContent().stream().map(roleMapper::roleEntityToRole).collect(Collectors.toList()));
		pageResponse.setPageNumber(pageNumber);
		pageResponse.setPageSize(rolePage.getSize());
		pageResponse.setTotalPages(rolePage.getTotalPages());
		pageResponse.setTotalElements(rolePage.getTotalElements());
		pageResponse.setFirst(rolePage.isFirst());
		pageResponse.setLast(rolePage.isLast());
		pageResponse.setNumberOfElements(rolePage.getNumberOfElements());
		return pageResponse;
	}

	@Override
	public String addRole(RoleRequest role) {

		System.out.println("inside addRole");
		Role roleEntity = roleMapper.roleToRoleEntity(role);

		System.out.println("testttt");

		roleRepository.save(roleEntity);
		return "Success";
	}

	@Override
	public RoleRequest getRole(UUID id) {
		Role roleEntity = roleRepository.findById(id).orElse(null);
		if (roleEntity != null) {
			return roleMapper.roleEntityToRole(roleEntity);
		}
		return null;
	}

	@Override
	public RoleRequest updateRole(UUID id,RoleRequest role) {
		if (roleRepository.existsById(id)) {
            
			Role roleEntity = roleMapper.roleToRoleEntity(role);
			roleEntity.setId(id);
			roleRepository.save(roleEntity);
			return roleMapper.roleEntityToRole(roleEntity);

		}

		return null;
	}

	@Override
	public boolean deleteRoleByApplicationId(String applicationId) {
		if (roleRepository.existsByApplicationId(applicationId)) {
			
			List<Role> roleEntity = roleRepository.findByApplicationId(applicationId);
			roleEntity.forEach((role)->{
				roleRepository.deleteById(role.getId());
			}); 
			
			return true;
		}
		return false;
	}

	@Override
	public PageResponse<RoleRequest> getRoleByApplicationId(String applicationId,Integer pageNumber, Integer recordsPerPage) {
		
		Pageable pageable = PageRequest.of(pageNumber, recordsPerPage);
		Page<Role> rolePage = roleRepository.findByPageApplicationId(applicationId,pageable);

		PageResponse<RoleRequest> pageResponse = new PageResponse<>();
		pageResponse.setContent(
				rolePage.getContent().stream().map(roleMapper::roleEntityToRole).collect(Collectors.toList()));
		pageResponse.setPageNumber(pageNumber);
		pageResponse.setPageSize(rolePage.getSize());
		pageResponse.setTotalPages(rolePage.getTotalPages());
		pageResponse.setTotalElements(rolePage.getTotalElements());
		pageResponse.setFirst(rolePage.isFirst());
		pageResponse.setLast(rolePage.isLast());
		pageResponse.setNumberOfElements(rolePage.getNumberOfElements());
		return pageResponse;
		/*
		 * List<Role> roleEntity = roleRepository.findByApplicationId(applicationId); if
		 * (roleEntity != null) { return roleMapper.roleEntityToRole(roleEntity); }
		 * return null;
		 */
	}

	@Override
	public boolean deleteRoleByApplicationRoleId(String applicationId,UUID roleId) {
		if (roleRepository.existsByApplicationId(applicationId))
		{
			roleRepository.deleteById(roleId);
		}
		return false;
	}

	@Override
	public List<RoleRequest> getRoleByApplicationUserId(String applicationId, String userId) {
if (roleRepository.existsByApplicationUserId(applicationId,userId)) {
			
			List<Role> roleEntity = roleRepository.findByApplicationUserIdFor(applicationId,userId);
			
			if(roleEntity!=null)
			return roleMapper.roleEntityToRole(roleEntity);
		}
		return null;
	}

}
