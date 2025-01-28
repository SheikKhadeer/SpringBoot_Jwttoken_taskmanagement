package com.taskManagement.service;

import java.util.List;

import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Users;

public interface UserService {

	public UserDto createUser(UserDto userDto);

	public List<UserDto> getAllUsers();

	
	public String deleteUser(long userid);

	List<UserDto> getAllActiveUsers();

	void deactivateUser(Long userid);

	Users dtoToEntity(UserDto userDto);

	UserDto entityToDto(Users users);

	public UserDto getUserByName(String name);

	public UserDto getUserById(Long userId);
	

}
