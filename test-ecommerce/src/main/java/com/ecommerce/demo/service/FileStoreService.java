package com.ecommerce.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.demo.config.StorageProperties;
import com.ecommerce.demo.exception.StorageException;

@Service
public class FileStoreService {
	private StorageProperties properties = new StorageProperties();
	Path rootLocation = Paths.get(properties.getLocation());
	
	public String store(MultipartFile file) {
		try {
			if(file.isEmpty()) {
				throw new StorageException("Fail to store empty file.");
			}
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String uploadFileName = UUID.randomUUID().toString() + "." + extension;
			
			Path destinationFile = rootLocation.resolve(Paths.get(uploadFileName)).normalize().toAbsolutePath();
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				
				final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
				
				return baseUrl + "/fileUpload/files" + uploadFileName;
			}
			
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}
	
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1)
					.filter(path -> !path.equals(rootLocation))
					.map(rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files.", e);
		}
	}
	
	public Resource load(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new RuntimeException("Could not read the file !");
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}
}
