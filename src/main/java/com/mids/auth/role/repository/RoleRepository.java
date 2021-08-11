package com.mids.auth.role.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import com.mids.auth.role.entity.Role;
import com.mids.auth.role.response.RoleResponse;



@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	Role findByRoleName(String name);
	
	Optional<Role> findById(Long id);
	
	List<Role> findByApplicationId(int applicationId);
	
	Boolean existsByApplicationId(int applicationId);
    
	Boolean existsByRoleName(String name);
	
}
