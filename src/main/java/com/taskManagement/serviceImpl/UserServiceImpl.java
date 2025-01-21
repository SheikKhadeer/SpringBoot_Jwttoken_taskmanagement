package com.taskManagement.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Users;
import com.taskManagement.repository.UsersRepository;
import com.taskManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		// Userdto is not an entity of users
		Users users = userDtotoEntity(userDto);// convert userdto to users

		Users savedUser = usersRepository.save(users);
		return entitytoUserDto(savedUser);
	}

	private Users userDtotoEntity(UserDto userDto) {
		Users users = new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
	}

	private UserDto entitytoUserDto(Users savedUser) {
		UserDto userdto = new UserDto();
		userdto.setId(savedUser.getId());
		userdto.setName(savedUser.getName());
		userdto.setEmail(savedUser.getEmail());
		userdto.setPassword(savedUser.getPassword());
		return userdto;
	}
}
