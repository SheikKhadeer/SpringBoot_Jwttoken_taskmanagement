//package com.taskManagement.service;
//
//import static org.hamcrest.CoreMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.taskManagement.dto.UserDto;
//import com.taskManagement.entity.Users;
//import com.taskManagement.repository.UsersRepository;
//import com.taskManagement.serviceImpl.UserServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//	@Mock
//	private UsersRepository usersRepository;
//
//	@Mock
//	private PasswordEncoder passwordEncoder;
//
//	@InjectMocks
//	private UserServiceImpl userServiceImpl;
//
//	@Test
//	void createUser_shouldReturnSavedUserDto_whenValidUserDtoProvided() {
//		// arrange
//		UserDto userDto = new UserDto(null, "abcd", "abcd@gmail.com", "abcd");
//
//		Users users = new Users();
//		users.setId(1l);
//		users.setName("abcd");
//		users.setEmail("abcd@gmail.com");
//		users.setPassword("abcd");
//
//		when(passwordEncoder.encode(userDto.getPassword())).thenReturn("abcd");
//
//		when(usersRepository.save(any(Users.class))).thenReturn(users);
//
//		// act
//
//		UserDto result = userServiceImpl.createUser(userDto);
////		assertNotNull(result);
////		assertEquals(1L, result.getId());
////		assertEquals("abcd", result.getTaskname());
////		assertEquals("abcd@gmail.com", result.getEmail());
////		assertEquals("abcd", result.getPassword());
//
//		verify(usersRepository, times(1)).save((S) any(Users.class));
//	}
//
//}
