package com.project.blogApp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blogApp.entities.User;
import com.project.blogApp.exceptions.ResourceNotFoundException;
import com.project.blogApp.repositories.UserRepositories;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	
	@Autowired
	private UserRepositories userRepositories;

	
	// loading user from database by username
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user =  this.userRepositories.findByEmail(username)
		.orElseThrow(() -> new ResourceNotFoundException("User" , "Email" , username));
		
		return user;
	}
}
