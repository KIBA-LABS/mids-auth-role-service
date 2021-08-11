
package com.mids.auth.customer.entity;




import javax.persistence.*;
import javax.validation.constraints.Email;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers", uniqueConstraints = { @UniqueConstraint(columnNames = "name"),
		@UniqueConstraint(columnNames = "customerid"), @UniqueConstraint(columnNames = "email") })
public class Register {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	@Size(max = 50)
	private String customerName;

	@Column(name = "email", nullable = false)
	@Size(max = 50)
	@Email
	private String email;

	@Column(name = "customerid", nullable = false)
	@Size(max = 50)
	private String customerId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "address", nullable = false)
	@Size(max = 150)
	private String address;

	@Column(name = "contact_person", nullable = false)
	@Size(max = 50)
	private String contactPerson;

	@Column(name = "contact_number", nullable = false)
	@Size(max = 50)
	private String contactNumber;

	@Column(name = "contact_email", nullable = false)
	@Size(max = 50)
	private String contactEmail;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private EStatus status;
	
	
	
	

	

	
}
