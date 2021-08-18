package com.mids.auth.role.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mids.auth.role.entity.RoleEmbeddedKey;
import com.mids.auth.role.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

	
	
	boolean existsByKey(RoleEmbeddedKey key); 
	
	@Query(value="select u.key.roleId from UserRole u join Role r on r.id=u.key.roleId   where r.applicationId=:applicationId and u.key.userId=:userId")
	List<UUID> getRoleIdByApplicationUserId(int applicationId,String userId);

	UserRole findByKey(RoleEmbeddedKey key);

}
