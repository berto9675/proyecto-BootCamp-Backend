package com.BootCamp.Backend.ProyectoBackend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.BootCamp.Backend.ProyectoBackend.model.ResourceModel;
import com.BootCamp.Backend.ProyectoBackend.repository.ResourceRepository;
@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;
    


    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ResourceModel storeResource(ResourceDTO resourceDTO) throws IOException {
        ResourceModel resource = new ResourceModel(resourceDTO.getTitle(), resourceDTO.getFileUrl(), resourceDTO.getCategory());
        return resourceRepository.save(resource);
    }

    public boolean deleteResource(Long id) {
        Optional<ResourceModel> resource = resourceRepository.findById(id);
        if (resource.isPresent()) {
            resourceRepository.delete(resource.get());
            return true;
        }
        return false;
    }

    public List<ResourceModel> getAllResources() {
        return resourceRepository.findAll();
    }

    public Optional<ResourceModel> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public List<ResourceModel> getResourcesByCategory(String category) {
        return resourceRepository.findByCategory(category);
    }

    public Optional<ResourceModel> updateResource(Long id, ResourceDTO resourceDTO) {
        Optional<ResourceModel> existingResource = resourceRepository.findById(id);
        if (existingResource.isPresent()) {
            ResourceModel resource = existingResource.get();
            resource.setTitle(resourceDTO.getTitle());
            resource.setFileUrl(resourceDTO.getFileUrl());
            resource.setCategory(resourceDTO.getCategory());
            return Optional.of(resourceRepository.save(resource));
        }
        return Optional.empty();
    }

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    public ResponseEntity<Resource> getFile(String filename) {
        try {
            Path filePath = getFilePath(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("El archivo no existe o no es accesible.");
            }

            return buildResponse(resource, filePath);
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Error al obtener el archivo", e);
        }
    }

    private Path getFilePath(String filename) {
        return Paths.get(UPLOAD_DIR, filename).normalize();
    }

    private ResponseEntity<Resource> buildResponse(Resource resource, Path filePath) throws IOException {
        String contentType = Files.probeContentType(filePath);
        contentType = (contentType != null) ? contentType : "application/pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                .body(resource);
    }
}