package com.BootCamp.Backend.ProyectoBackend.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.ace_code.ace_code_backend.model.ResourceDTO;
import dev.ace_code.ace_code_backend.model.ResourceModel;
import dev.ace_code.ace_code_backend.repository.ResourceRepository;

public class ResourceServiceTest {
    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    private ResourceModel resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = new ResourceModel("title", "url", "documentation");
    }

    @Test
    @DisplayName("Test que comprueba la lógica para guardar archivos")
    void storeResourceTest() throws IOException {

        ResourceDTO dto = new ResourceDTO();
        dto.setTitle("title");
        dto.setFileUrl("url");
        dto.setCategory("category");

        ResourceModel model = new ResourceModel(dto.getTitle(), dto.getFileUrl(), dto.getCategory());

        when(resourceRepository.save(any(ResourceModel.class))).thenReturn(model);

        ResourceModel saved = resourceService.storeResource(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo(dto.getTitle());
        verify(resourceRepository).save(any(ResourceModel.class));
    }
    @Test
    @DisplayName("Test que comprueba la lógica para eliminar archivos")
    public void deleteResourceTest() {

        Long resourceId = 1L;

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        boolean deleted = resourceService.deleteResource(resourceId);

        assertThat(deleted).isTrue();
        verify(resourceRepository, times(1)).delete(resource);
    }

    @Test
    @DisplayName("Test que comprueba la lógica para obtener todos los archivos")
    public void getAllResourcesTest() {

        List<ResourceModel> resources = List.of(
            resource,
            new ResourceModel("title2", "url2", "documentation")
        );

        when(resourceRepository.findAll()).thenReturn(resources);

        List<ResourceModel> result = resourceService.getAllResources();

        assertThat(result).isNotEmpty().hasSize(2);
        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test que comprueba la lógica para obtener un archivo por id")
    public void getResourceByIdTest() {
        
        Long resourceId = 1L;

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        Optional<ResourceModel> result = resourceService.getResourceById(resourceId);

        assertThat(result).isPresent().contains(resource);
        verify(resourceRepository, times(1)).findById(resourceId);
    }

    @Test
    @DisplayName("Test que comprueba la lógica para obtener archivos por categorías")
    public void getResourcesByCategoryTest() {
        
        String category = "documentation";
        List<ResourceModel> resources = List.of(
            resource,
            new ResourceModel("title2", "url2", category)
        );

        when(resourceRepository.findByCategory(category)).thenReturn(resources);

        List<ResourceModel> result = resourceService.getResourcesByCategory(category);

        assertThat(result).isNotEmpty().hasSize(2);
        verify(resourceRepository, times(1)).findByCategory(category);
    }

    @Test
    @DisplayName("Test que comprueba la lógica para actualizar el título, ruta o categoría de archivos")
    public void updateResourceTest() {

        Long resourceId = 1L;

        ResourceDTO dto = new ResourceDTO();
        dto.setTitle("new-title");
        dto.setFileUrl("new-url");
        dto.setCategory("new-category");

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(ResourceModel.class))).thenReturn(resource);

        Optional<ResourceModel> newResource = resourceService.updateResource(resourceId, dto);

        assertThat(newResource).isPresent();
        assertThat(newResource.get().getTitle()).isEqualTo(dto.getTitle());
        assertThat(newResource.get().getFileUrl()).isEqualTo(dto.getFileUrl());
        assertThat(newResource.get().getCategory()).isEqualTo(dto.getCategory());

        verify(resourceRepository, times(1)).findById(resourceId);
        verify(resourceRepository, times(1)).save(resource);
    }

    @Test
    @DisplayName("Test que comprueba la lógica para obtener archivos y poder visualizarlos en front")
    public void getFileTest() throws IOException {

        String filename = "test.pdf";
        Path filePath = Paths.get(System.getProperty("user.dir"), "uploads", filename);
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);

        ResponseEntity<Resource> response = resourceService.getFile(filename);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isInstanceOf(UrlResource.class);
        assertThat(response.getHeaders().getContentDisposition().toString()).contains(filename);

        Files.deleteIfExists(filePath);
    }

    @Test
    @DisplayName("Test que comprueba la lógica para obtener un archivo que no existe")
    public void getFileErrorTest() throws IOException {

        String filename = "null.pdf";

        assertThatThrownBy(() -> resourceService.getFile(filename))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al obtener el archivo");
    }

    @Test
    @DisplayName("Test que verifica que contentType toma el valor application/pdf si es null")
    public void getFileNullTest() throws IOException {

        String filename = "test.pdf";
        Path filePath = Paths.get(System.getProperty("user.dir"), "uploads", filename);
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.probeContentType(filePath)).thenReturn(null);

            ResponseEntity<Resource> response = resourceService.getFile(filename);

            assertThat(response.getHeaders().getContentType().toString()).isEqualTo("application/pdf");
        }

        Files.deleteIfExists(filePath);
    }
}