package com.mids.auth.role.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ApiKeyRoleEmbeddedKey implements Serializable{

	@Column(name = "apikey_id")
	private UUID apiKeyId;
	
	@Column(name = "role_id")
	private UUID roleId;

}
