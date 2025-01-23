package com.taskManagement.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskManagement.dto.LoginDto;
import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Users;
import com.taskManagement.security.JwtTokenProvider;
import com.taskManagement.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userServiceImpl;

	@InjectMocks
	private TaskController taskController;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void createUser_shouldReturnResponse_whenValidUserDtoProvided() {
		// arrange
		UserDto userDto = new UserDto(null, "abcd", "abcd@gmail.com", "abcd");
		UserDto savedUserDto = new UserDto(1L, "abcd", "abcd@gmail.com", "abcd123");
		
		when(userServiceImpl.createUser(any(Users.class))).thenReturn(savedUserDto);
		
		//act& assert
		mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id".valueOf(1))
				.andExpect(jsonPath("$.name".valueOf("abcd"))
				.andExpect(jsonPath("$.email".valueOf("abcd@gmail.com"));
		
		verify(userServiceImpl, times(1)).createUser(any(UserDto.class));
	}

	@Test
	void loginUser_shouldReturnToken_whenValidUserDtoProvided() {
		// arrange
		LoginDto loginDto = new LoginDto( "abcd@gmail.com", "abcd");
		String jwtToken = "mock-jwt-token";
		
		Authentication authentication=mock(Authentication.class);
		
		
//		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)).thenReturn(authentication);
		when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtToken);
		
		//act& assert
		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(loginDto)))
				.andExpect(status().isOk());
//				.andExpect(jsonPath("$.token".valueOf(jwtToken))
		
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
	verify(jwtTokenProvider,times(1)),generateToken(authentication));

}

}