package com.project.blogApp.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.blogApp.services.FileService;


@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		// FILE NAME
		
		String name = file.getOriginalFilename();
		
		// random name generator for file
		
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		// FULL PATH 
		
		String filePath = path + File.separator + fileName1;
		
		// CREATE FOLDER IF NOT CREATED 
		
		File f = new File(path);
		if (!f.exists())  f.mkdir();
		
		// FILE COPY
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getImage(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		
		InputStream inputStream = new FileInputStream(fullPath);
		
		return inputStream;
	}

	@Override
	public List<String> uploadImages(String path, MultipartFile[] files) throws IOException {
		List<String> fileStrings = new ArrayList<>();
		for (MultipartFile file : files) {
			// FILE NAME
			
			String name = file.getOriginalFilename();
			
			// random name generator for file
			
			String randomId = UUID.randomUUID().toString();
			String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
			
			// FULL PATH 
			
			String filePath = path + File.separator + fileName1;
			
			// CREATE FOLDER IF NOT CREATED 
			
			File f = new File(path);
			if (!f.exists())  f.mkdir();
			
			// FILE COPY
			
			Files.copy(file.getInputStream(), Paths.get(filePath));
			fileStrings.add(fileName1);
			
		}
		
		return fileStrings;
	}
	
	
	

}
