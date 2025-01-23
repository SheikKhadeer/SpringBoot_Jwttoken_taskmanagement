package com.taskManagement.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskManagement.controller.AuthController;
import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.Users;
import com.taskManagement.exception.UserNotFound;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.repository.UsersRepository;
import com.taskManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("Saving user...");
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		// Userdto is not an entity of users
		Users users = userDtotoEntity(userDto);// convert userdto to users

		Users savedUser = usersRepository.save(users);
		logger.info("User saved Sucessfully");
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

	@Override
	public List<UserDto> getAllUsers() {
		logger.info("Entering getAllUsers() in UserServiceImpl");
		List<Users> users = usersRepository.findAll();
		logger.debug("fetched {} users form the database ", users.size());
		return users.stream().map(user -> new UserDto(user.getId(), user.getEmail(), user.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public UserDto getUserById(long userId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));

		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public String deleteUser(long userId) {
		logger.info("Entering deleteUser() in UserServiceImpl");
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		logger.debug("Checking if user ID {} has any tasks ", userId);
		List<Task> tasks = taskRepository.findAllByUsersId(userId);
		if (!tasks.isEmpty()) {
			logger.warn("User cannot be deleted with ID {} because they have tasks", userId);
			throw new RuntimeException("User cannot be deleted because they have tasks");
		}

		usersRepository.deleteById(userId);
		logger.info("Deleted user ID {} Successfully", userId);
		return "user deleted succesfully";

		// TODO Auto-generated method stub

	}
}
