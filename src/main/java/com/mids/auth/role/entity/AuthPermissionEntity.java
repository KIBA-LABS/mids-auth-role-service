package com.mids.auth.role.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "auth_permissions")
public class AuthPermissionEntity {
    @Id
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    private String name;
}
