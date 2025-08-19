package com.oranbyte.recipebook.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oranbyte.recipebook.constant.AppConstants;
import com.oranbyte.recipebook.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FileServiceImpl implements FileService {
	
	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		if (file != null && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
			String uniqueName = UUID.randomUUID().toString() + extension;

			Path path = Paths.get(AppConstants.UPLOAD_DIR + uniqueName);
			Files.createDirectories(Paths.get(AppConstants.UPLOAD_DIR));

			Files.write(path, file.getBytes());

			return uniqueName;
		}
		return null;
	}

	@Override
	public String uploadFile(MultipartFile file, String directory) throws IOException {

		if (Objects.isNull(directory)) {
			directory = "";
		} else {
			directory = directory + "/";
		}

		if (file != null && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
			String uniqueName = UUID.randomUUID().toString() + extension;

			Path path = Paths.get(AppConstants.UPLOAD_DIR + directory + uniqueName);
			Files.createDirectories(Paths.get(AppConstants.UPLOAD_DIR + directory));

			Files.write(path, file.getBytes());

			return directory + uniqueName;
		}
		return null;
	}

	@Override
	public String getFullPath(String fileName) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = requestAttributes.getRequest();
			String appURL = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();

			if (new File((AppConstants.UPLOAD_DIR + fileName.trim())).exists()) {
				return appURL + "/uploads/" + fileName;
			} else {
				return null;
			}

		}
		return null;
	}

	@Override
	public boolean deleteIfExists(String filePath) {
		try {
			Path path = Paths.get(AppConstants.UPLOAD_DIR + filePath);
			return Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
