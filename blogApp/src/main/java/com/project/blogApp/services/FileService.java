package com.project.blogApp.services;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	
	String uploadImage(String path , MultipartFile file) throws IOException;
	
	InputStream getImage(String path , String fileName) throws FileNotFoundException;
	
	List<String> uploadImages(String path , MultipartFile[] files) throws IOException;
	

}
