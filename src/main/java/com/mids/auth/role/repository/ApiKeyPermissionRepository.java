package com.mids.auth.role.repository;

import com.mids.auth.role.entity.ApiKeyPermission;
import com.mids.auth.role.entity.ApiKeyPermissionEmbeddedKey;
import com.mids.auth.role.entity.PermissionEmbeddedKey;
import com.mids.auth.role.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiKeyPermissionRepository extends JpaRepository<ApiKeyPermission, ApiKeyPermissionEmbeddedKey> {
	
	boolean existsByKey(ApiKeyPermissionEmbeddedKey key);
	
	@Query(value="select u.key.permissionId from ApiKeyPermission u join Permission p on p.id=u.key.permissionId where p.roles.id=:roleId and u.key.apiKeyId=:apiKeyId")
	List<UUID> getPermissionIdByApplicationApiKeyId(UUID roleId,UUID apiKeyId);

	ApiKeyPermission findByKey(ApiKeyPermissionEmbeddedKey key);

}
