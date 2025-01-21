package com.taskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.dto.JwtAuthenticationResponse;
import com.taskManagement.dto.LoginDto;
import com.taskManagement.dto.UserDto;
import com.taskManagement.security.JwtTokenProvider;
import com.taskManagement.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	// post store the user in db
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody LoginDto loginDto){
		Authentication authentication=authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
	System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//get the jwt token
		String token = jwtTokenProvider.generateToken(authentication);
//		return new ResponseEntity<String>("User loggedIn succesfully", HttpStatus.OK);
		return  ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
}
