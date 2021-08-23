package com.mids.auth.role.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.mids.auth.role.entity.Role;
import com.mids.auth.role.response.RoleResponse;



@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	Role findByRoleName(String name);
	
	Optional<Role> findById(Long id);
	
	List<Role> findByApplicationId(String applicationId);
	
	//List<Role> findByApplicationIdForDelete(String applicationId);
	
	Boolean existsByApplicationId(String applicationId);
    
	Boolean existsByRoleName(String name);
	
	@Query("select case when count(c)> 0 then true else false end from Role c where c.id=:id and c.applicationId=:applicationId")
	boolean existsByRoleAndApplicationId(UUID id,String applicationId);
    
	@Query("select case when count(c)> 0 then true else false end from Role c join UserRole u on c.id=u.key.roleId where u.key.userId=:userId and c.applicationId=:applicationId")
	boolean existsByApplicationUserId(String applicationId, String userId);

	@Query("select r from Role r join UserRole u on r.id=u.key.roleId where u.key.userId=:userId and r.applicationId=:applicationId")
	List<Role> findByApplicationUserIdFor(String applicationId, String userId);

	@Query("select r from Role r  where  r.applicationId=:applicationId")
	Page<Role> findByPageApplicationId(@Param("applicationId") String applicationId, Pageable pageable);
	
}
