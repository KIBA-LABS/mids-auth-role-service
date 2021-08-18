package com.mids.auth.role.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "roles",uniqueConstraints=
@UniqueConstraint(columnNames={"applicationid", "name"}))

public class Role {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID id;
	
	@Column(name = "applicationid")
	@Size(max = 200)
	private String applicationId;
	
	@Column(name = "name")
	@Size(max = 50)
	@NotBlank
	private String roleName;
	
	@JsonIgnore
	 @OneToMany(cascade = CascadeType.ALL,mappedBy="roles")
	    private List<Permission> permissions=new ArrayList<>();
	
	

}
