package com.taskManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	public UserDto(Long id2, String email2, String name2) {
		this.email = email2;
		this.name = name2;
		this.id = id2;
	}

	private Long id;
	private String name;
	private String email;
	private String password;
}
