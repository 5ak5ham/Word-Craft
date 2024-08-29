package com.project.blogApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogApp.entities.User;

public interface UserRepositories extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
