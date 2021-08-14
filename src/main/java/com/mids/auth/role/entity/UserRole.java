package com.mids.auth.role.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "users_roles" ,uniqueConstraints=
@UniqueConstraint(columnNames={"user_id", "role_id"}))
public class UserRole {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID id;

	
	@Column(name = "user_id",length = 50)
	@Size(max = 50)
	@NotBlank
	private String userId;
	
	@Column(name = "role_id")
	private UUID roleId;

}
