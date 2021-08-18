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
	
	List<Role> findByApplicationId(String applicationId);
	
	Boolean existsByApplicationId(String applicationId);
    
	Boolean existsByRoleName(String name);
	
	@Query("select case when count(c)> 0 then true else false end from Role c where id=:id and applicationid=:applicationId")
	boolean existsByRoleAndApplicationId(UUID id,String applicationId);
	
}
