package com.mids.auth.role.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "apikeys_permissions")
public class ApiKeyPermission {
	
	@EmbeddedId
	private ApiKeyPermissionEmbeddedKey key;

}
