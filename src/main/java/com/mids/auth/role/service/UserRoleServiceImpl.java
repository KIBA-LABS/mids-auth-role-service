package com.mids.auth.role.service;



import java.util.List;
import java.util.UUID;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mids.auth.role.entity.Role;
import com.mids.auth.role.entity.UserRole;
import com.mids.auth.role.mapper.RoleMapper;
import com.mids.auth.role.repository.RoleRepository;
import com.mids.auth.role.repository.UserRoleRepository;
import com.mids.auth.role.request.UserRoleRequest;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleMapper roleMapper;

	@Autowired
	public UserRoleServiceImpl(RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.roleMapper = Mappers.getMapper(RoleMapper.class);
	}

	@Override
	public String addUserRole(UserRoleRequest userRoleRequest,String applicationId, String userId) {
		List<UUID> roles = userRoleRequest.getRole();
		
		roles.forEach((role)->{
			if(roleRepository.existsByRoleAndApplicationId(role,Integer.parseInt(applicationId)))
					{
				UserRole userRoleEntity = new UserRole();
				userRoleEntity.setUserId(userId);
				userRoleEntity.setRoleId(role);
				userRoleRepository.save(userRoleEntity);
					}
		}); 
		
			return "Success";
		
	}
	
	
	
	
	

}
