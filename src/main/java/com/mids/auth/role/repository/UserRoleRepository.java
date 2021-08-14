package com.mids.auth.role.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mids.auth.role.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> { 

}
