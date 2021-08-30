package com.mids.auth.role.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "apikeys_roles")
public class ApiKeyRole {
	@EmbeddedId
	private ApiKeyRoleEmbeddedKey key;

}
