package com.taskManagement.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskManagement.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;

	@NotBlank(message = "Name connot be empty")
	@Size(min = 3, max = 35, message = "name must be between 3 and 35 characters:")
	private String name;

	@NotBlank(message = "Email cant be empty")
	@Email(message = "Email must be valid:")
	private String email;

	@NotBlank(message = "password cant be empty")
	@Size(min = 8, message = "password must be atleast 8 characters long")
	private String password;

	@NotBlank(message = "phoneNumber cant be empty")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	private String phoneNumber;

	@NotBlank(message = "address cannot be empty")
	private String address;

	@NotNull(message = "gender cannot be null")
	private Gender gender;

	@NotNull(message = "Dateof birth cannog be null")
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;

	private boolean isActive;
	
	private Date createdAt;
	
	private Date updatedAt;

}
