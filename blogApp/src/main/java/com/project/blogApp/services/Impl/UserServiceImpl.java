package com.project.blogApp.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blogApp.config.AppConstants;
import com.project.blogApp.entities.Roles;
import com.project.blogApp.entities.User;
import com.project.blogApp.payload.UserDT;
import com.project.blogApp.exceptions.*;
import com.project.blogApp.repositories.RoleRepositories;
import com.project.blogApp.repositories.UserRepositories;
import com.project.blogApp.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepositories roleRepo;

	@Override
	public UserDT createUser(UserDT userDt) {
		
		User user = this.dtToUser(userDt);
		user.setImageName("default.png");
		User savedUser = this.userRepositories.save(user);
		
		return this.userToDt(savedUser);
	}

	@Override
	public UserDT updateUser(UserDT userDt, int userId) {
		
		User user = this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		
		user.setAbout(userDt.getAbout());
		user.setPassword(userDt.getPassword());
		user.setName(userDt.getName());
		user.setEmail(userDt.getEmail());
		user.setImageName(userDt.getImageName());
		
		User updatedUser = this.userRepositories.save(user);
		return this.userToDt(updatedUser);
	}

	@Override
	public UserDT getUserById(int userId) {
		User user = this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		
		return this.userToDt(user);
	}

	@Override
	public List<UserDT> getAllUser() {
		List<User> users = this.userRepositories.findAll();
		
		List<UserDT> userDTs = users.stream().map(user -> this.userToDt(user)).collect(Collectors.toList());
		return userDTs;
	}

	@Override
	public void deleteUser(int userId) {
		User user = this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id " , userId ));
		this.userRepositories.delete(user);
	}
	
	public User dtToUser(UserDT userDt) {
	/*	User user = new User();
		user.setId(userDt.getId());
		user.setName(userDt.getName());
		user.setEmail(userDt.getEmail());
		user.setAbout(userDt.getAbout());
		user.setPassword(userDt.getPassword());*/
		User user = this.modelMapper.map(userDt, User.class);
		return user;	
	}
	
	
	public UserDT userToDt(User user) {
	/*	UserDT userDt = new UserDT();
		userDt.setId(user.getId());
		userDt.setName(user.getName());
		userDt.setEmail(user.getEmail());
		userDt.setAbout(user.getAbout());
		userDt.setPassword(user.getPassword());*/
		UserDT userDt = this.modelMapper.map(user, UserDT.class);
		return userDt;	
	}

	@Override
	public UserDT registerNewUser(UserDT userDt) {
		
		User user = this.modelMapper.map(userDt, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		
		Roles role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User saveUser = this.userRepositories.save(user);
		
		return this.modelMapper.map(saveUser, UserDT.class);
	}

}
