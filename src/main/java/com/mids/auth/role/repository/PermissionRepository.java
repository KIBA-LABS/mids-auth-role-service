package com.mids.auth.role.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.mids.auth.role.entity.EPermission;
import com.mids.auth.role.entity.Permission;
import com.mids.auth.role.entity.PermissionEmbeddedKey;
import com.mids.auth.role.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	
	Permission findByPermission(String name);

	List<Permission> findByRoles(Role roleEntity);
	
	
	@Query("Select p from Permission p  JOIN p.roles r  where r.applicationId=:applicationId")
	List<Permission> findByApplicationId(String applicationId);
	
	@Query("Select p from Permission p  JOIN p.roles r  where r.applicationId=:applicationId and r.id=:roleId")
	List<Permission> findByApplicationRoleId(String applicationId,UUID roleId);
	
	@Query("Select p.id from Permission p  JOIN p.roles r  where r.applicationId=:applicationId and r.id=:roleId")
	List<UUID> findPermissionApplicationRoleId(String applicationId,UUID roleId);
	
	@Query("select case when count(p)> 0 then true else false end  from Permission p  JOIN p.roles r  where r.applicationId=:applicationId and p.id=:id")
	boolean existsByPermissionRoleAndApplicationId(UUID id,String applicationId);
	@Query("select p.roles.id from Permission p  JOIN p.roles r  where r.applicationId=:applicationId and p.id=:permission")
	UUID findByPermissionApplicationId(UUID permission, String applicationId);
    
	@Query(value="select p from Permission p join UserPermission u on p.id=u.key.permissionId where p.roles.id in (select r.key.roleId from UserRole r join Role c on c.id=r.key.roleId where r.key.userId=:userId and c.applicationId=:applicationId) and u.key.userId=:userId")
	List<Permission> findByApplicationUserId(String applicationId, String userId);

	
}
