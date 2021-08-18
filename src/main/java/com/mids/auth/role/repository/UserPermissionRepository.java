package com.mids.auth.role.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mids.auth.role.entity.PermissionEmbeddedKey;
import com.mids.auth.role.entity.RoleEmbeddedKey;
import com.mids.auth.role.entity.UserPermission;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, UUID> {
	
	boolean existsByKey(PermissionEmbeddedKey key);
	
	@Query(value="select u.key.permissionId from UserPermission u join Permission p on p.id=u.key.permissionId where p.roles.id=:roleId and u.key.userId=:userId")
	List<UUID> getPermissionIdByApplicationUserId(UUID roleId,String userId);

	UserPermission findByKey(PermissionEmbeddedKey key);

}
