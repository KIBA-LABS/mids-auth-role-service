package com.mids.auth.role.entity;

import java.util.List;
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

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "roles_permissions" ,uniqueConstraints=
@UniqueConstraint(columnNames={"permission", "role_id"}))
public class Permission {

	@Id
	@GeneratedValue(generator = "uuid")
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID id;

	
	@Column(length = 50)
	@Size(max = 50)
	@NotBlank
	private String permission;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "role_id")
	@NotBlank
	private Role roles;

	

}
