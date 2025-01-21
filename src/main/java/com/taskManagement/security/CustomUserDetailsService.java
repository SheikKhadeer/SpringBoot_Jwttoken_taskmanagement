package com.taskManagement.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskManagement.entity.Users;
import com.taskManagement.exception.UserNotFound;
import com.taskManagement.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByEmail(username)
				.orElseThrow(() -> new UserNotFound(String.format("user with email : %d is not found", username)));
		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_ADMIN");
		return new User(user.getEmail(), user.getPassword(), userAuthorities(roles));
	}

	private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

	}
}
