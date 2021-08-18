package com.mids.auth.role.entity;




import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "users_permissions")
public class UserPermission {
	
	@EmbeddedId
	private PermissionEmbeddedKey key;

}
