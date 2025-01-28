package com.taskManagement.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskManagement.cache.CacheConfig;
import com.taskManagement.cache.CacheInspect;
import com.taskManagement.controller.AuthController;
import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.Users;
import com.taskManagement.exception.UserNotFound;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.repository.UsersRepository;
import com.taskManagement.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
//	@Autowired
//	private CacheInspect cacheInspect;
//	@Autowired
//	private CacheManager cacheManager;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("Saving user...");
		
		System.out.println(userDto.getPassword());
		if(userDto.getPassword()==null|| userDto.getPassword().isEmpty())
			throw new IllegalArgumentException("Password should not be null");
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

		// convert userdto to users entity
		Users userEntity = dtoToEntity(userDto);

		// save user entity
		Users savedUser = usersRepository.save(userEntity);
		logger.info("User saved Sucessfully");

		return entityToDto(savedUser);
	}

@Cacheable(value="users")
	@Override
	public List<UserDto> getAllUsers() {
		logger.info("Entering getAllUsers() in UserServiceImpl");
		List<Users> users = usersRepository.findAll();
		logger.debug("fetched {} users form the database ", users.size());
		return users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
	}

	@Cacheable(value="users",key="#userId")
	@Override
	public UserDto getUserById(Long userId) {
//		cacheInspect.inspectCache(userId);
		logger.info("fetching user with id : {}", userId);
		Users userEntity = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user not found with ID : %d", userId)));
		
//		Cache cache= cacheManager.getCache("users");
//	
//		if(cache!=null)
//			System.out.println("initial cahce content :"+ cache.get(userId));
		
		return entityToDto(userEntity);
	}

	@Override
	public List<UserDto> getAllActiveUsers() {
		logger.info("fetching all active users...");
		List<Users> users= usersRepository.findAllActiveUsers();
		return users.stream()
				.map(user->entityToDto(user))
				.collect(Collectors.toList());
	}


	@Override
	public void deactivateUser(Long userid) {
		logger.info("Deactivating user with ID:  {}", userid);
		UserDto user = getUserById(userid);
		Users u=dtoToEntity(user);
		u.setActive(false);
		usersRepository.save(u);

	}

	@CacheEvict(value="users", key="#userId")
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

	@Override
	public Users dtoToEntity(UserDto userDto) {
		return modelMapper.map(userDto, Users.class);
	}

	@Override
	public UserDto entityToDto(Users user) {
		UserDto userDto=modelMapper.map(user, UserDto.class);
		userDto.setCreatedAt(user.getCreatedAt());
		userDto.setUpdatedAt(user.getUpdatedAt());
		userDto.setPassword(null);
		return userDto;
	}

	@Override
	public UserDto getUserByName(String name) {
		logger.info("Searching users by name {}", name);
		return usersRepository.findByName(name);
	}

	
}
