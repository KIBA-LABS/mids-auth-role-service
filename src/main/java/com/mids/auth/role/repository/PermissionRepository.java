package com.mids.auth.role.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.mids.auth.role.entity.EPermission;
import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	
	Permission findByPermission(String name);

	List<Permission> findByRoles(Role roleEntity);
	
	
	@Query("Select p from Permission p  JOIN p.roles r  where r.applicationId=:applicationId")
	List<Permission> findByApplicationId(int applicationId);
	
	@Query("Select p from Permission p  JOIN p.roles r  where r.applicationId=:applicationId and r.id=:roleId")
	List<Permission> findByApplicationRoleId(int applicationId,UUID roleId);
}
