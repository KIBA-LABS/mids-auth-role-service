package com.mids.auth.role.entity;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PermissionEmbeddedKey implements Serializable {
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "permission_id")
	private UUID permissionId;

}
