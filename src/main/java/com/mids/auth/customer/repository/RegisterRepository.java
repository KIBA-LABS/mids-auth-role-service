package com.mids.auth.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.mids.auth.customer.entity.Register;


@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
	// Optional<Register> findByClientId(String clientId);

	Register findByCustomerId(String clientId);

	Boolean existsByCustomerId(String clientId);

	Boolean deleteByCustomerId(String clientId);

	Boolean existsByEmail(String email);

}
