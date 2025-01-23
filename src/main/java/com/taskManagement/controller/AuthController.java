package com.taskManagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.dto.JwtAuthenticationResponse;
import com.taskManagement.dto.LoginDto;
import com.taskManagement.dto.UserDto;
import com.taskManagement.exception.UserNotFound;
import com.taskManagement.security.JwtTokenProvider;
import com.taskManagement.service.UserService;

@RestController
@RequestMapping("/api/users")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	// post store the user in db
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		logger.info("fetching all users...");
		List<UserDto> users = userService.getAllUsers();
		logger.info("fetched {} users succesfully", users.size());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userid") long userid) {
		return new ResponseEntity<>(userService.getUserById(userid), HttpStatus.OK);
	}

	// delete the user
	@DeleteMapping("/delete/{userid}")
	public ResponseEntity<String> deleteTask(@PathVariable(name = "userid") long userid) {

		logger.info("Attempting to delete user with ID {} ", userid);
		try {
		String response = userService.deleteUser(userid);
		logger.info("User with ID {} deleted successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(UserNotFound ex) {
			logger.error("failed to delete user with ID {} :{}",userid,ex.getMessage());
		throw ex;
		}
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get the jwt token
		String token = jwtTokenProvider.generateToken(authentication);
//		return new ResponseEntity<String>("User loggedIn succesfully", HttpStatus.OK);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
}
