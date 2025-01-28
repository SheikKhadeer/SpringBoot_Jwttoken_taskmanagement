package com.taskManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taskManagement.dto.UserDto;
import com.taskManagement.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String username);

	
	@Query("select u from Users u where u.isActive= true")
	List<Users> findAllActiveUsers();


	UserDto findByName(String name);

}
