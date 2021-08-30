package com.mids.auth.role.repository;

import com.mids.auth.role.entity.ApiKeyRole;
import com.mids.auth.role.entity.ApiKeyRoleEmbeddedKey;
import com.mids.auth.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiKeyRoleRepository extends JpaRepository<ApiKeyRole, ApiKeyRoleEmbeddedKey> {

    boolean existsByKey(ApiKeyRoleEmbeddedKey key);

    @Query(value = "select u.key.roleId from ApiKeyRole u join Role r on r.id=u.key.roleId   where r.applicationId=:applicationId and u.key.apiKeyId=:apiKeyId")
    List<UUID> getRoleIdByApiKeyId(String applicationId, UUID apiKeyId);

    ApiKeyRole findByKey(ApiKeyRoleEmbeddedKey key);

    @Query("select case when count(c)> 0 then true else false end from Role c join ApiKeyRole u on c.id=u.key.roleId where u.key.apiKeyId=:apiKeyId and c.applicationId=:applicationId")
    boolean existsByApplicationApiKeyId(String applicationId, UUID apiKeyId);

    @Query("select r from Role r join ApiKeyRole u on r.id=u.key.roleId where u.key.apiKeyId=:apiKeyId and r.applicationId=:applicationId")
    List<Role> findByApplicationApiKeyId(String applicationId, UUID apiKeyId);
}
