package com.empresa.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource load(String filename) throws MalformedURLException;
	
	public String copy(MultipartFile file) throws IOException;
	
	public boolean delete(String filename);
	
	// Recursively delete the 'uploads' directory
	public void deleteAll();
	
	// Recreate the directory 'uploads'
	public void init() throws IOException;

}
