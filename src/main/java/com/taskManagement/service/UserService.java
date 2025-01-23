package com.taskManagement.service;

import java.util.List;

import com.taskManagement.dto.UserDto;

public interface UserService {

	public UserDto createUser(UserDto userDto);

	public List<UserDto> getAllUsers();

	public UserDto getUserById(long userid);

	public String deleteUser(long userid);
}
