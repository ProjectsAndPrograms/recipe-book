package com.oranbyte.recipebook.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String uploadFile(MultipartFile multipartFile) throws IOException;

	String uploadFile(MultipartFile multipartFile, String directory) throws IOException;

	String getFullPath(String fileName);

	boolean deleteIfExists(String filePath);
}
