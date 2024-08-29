package com.project.blogApp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//DT STANDS FOR DATA TRANSFER
@Getter
@Setter
@NoArgsConstructor
public class UserDT {

	private int id;
	
	@NotEmpty
	@Size(min = 2, message = "Name should contain atleast 2 or more characters")
	private String name;
	
	@NotEmpty
	private String about;
	
	@NotEmpty
	@Size(min = 4 , max = 15 , message = "Length of password should not be less than 4, and should not exceed 15 characters")
	private String password;
	
	@Email(message = "Email address not valid")
	private String email;
	
	
	private String imageName;
}
