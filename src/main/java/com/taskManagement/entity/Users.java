package com.taskManagement.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskManagement.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@Data
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	@NotBlank(message = "Name connot be empty")
	@Size(min = 3, max = 35, message = "name must be between 3 and 35 characters:")
	private String name;

	@Column(name = "email", nullable = false)
	@Email(message = "Email must be valid:")
	private String email;

	@Column(name = "password", nullable = false)
	@Size(min = 8, max = 35, message = "pasword must be atleast 8 characters long")
	@JsonIgnore
	private String password;

	@Column(name = "phone_number", nullable = false)
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String phoneNumber;

	@Column(name = "address", nullable = false)
	@NotBlank(message = "address cannot be empty")
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;

	@Column(name = "date_of_birth", nullable = false)
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.isActive = true;

	}
	
	@PreUpdate
	protected void onUpdate() {
		
		this.updatedAt = new Date();

	}
	

	
}
