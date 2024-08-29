package com.project.blogApp.services;

import java.util.List;
import com.project.blogApp.payload.UserDT;

public interface UserService {
	
	UserDT registerNewUser(UserDT userDt);
	
	UserDT createUser(UserDT userDt);
	
	UserDT updateUser(UserDT userDt , int userId);
	
	UserDT getUserById(int userId);
	
	List<UserDT> getAllUser();
	
	void deleteUser(int userId);
}
