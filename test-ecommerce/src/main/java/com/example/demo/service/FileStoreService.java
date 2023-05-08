package com.example.demo.service;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.example.demo.config.StorageProperties;

@Service
public class FileStoreService {
	private StorageProperties properties = new StorageProperties();
	Path rootLocation = Paths.get(properties.getLocation()); // src/main/resources
	
	public Resource load(String filename) {
		try {

			String projectPath = System.getProperty("user.dir");

			Path file = Paths.get(projectPath + "/src/main/webapp/" + filename);

			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new RuntimeException("Could not read the file !");
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public Path getRelativePath() {

		// Lấy path tương đối của project
		String projectPath = System.getProperty("user.dir");

		// Lấy path tương đối của thư mục gốc
		Path path = Paths.get(projectPath + "/src/main/webapp/");

		return path;
	}
}
