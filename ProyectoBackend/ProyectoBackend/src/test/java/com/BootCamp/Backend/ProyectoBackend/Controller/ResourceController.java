package com.BootCamp.Backend.ProyectoBackend.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.Resource;
import com.BootCamp.Backend.ProyectoBackend.Service.ResourceService;
import com.BootCamp.Backend.ProyectoBackend.Controller.ResourceController;
import com.BootCamp.Backend.ProyectoBackend.Model.ResourceModel;
import com.BootCamp.Backend.ProyectoBackend.DTO.ResourceDTO;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceControllerTest {

    private final ResourceService service = mock(ResourceService.class);

    private ResourceController controller;

    @BeforeEach
    void setUp() {
        controller = new ResourceController(service);
    }

    @Test
    void uploadResource() throws IOException {
        final var resourceDTO = new ResourceDTO();
        final var expected = ResponseEntity.ok("Archivo subido con éxito");

        when(service.storeResource(resourceDTO)).thenReturn(null);

        final var actual = controller.uploadResource(resourceDTO);

        assertEquals(expected, actual);
    }

    @Test
    void deleteResourceShouldBeRemoved() {
        final var id = 1L;
        final var expected = ResponseEntity.ok("Recurso eliminado correctamente.");

        when(service.deleteResource(id)).thenReturn(true);
        final var actual = controller.deleteResource(id);

        assertEquals(expected, actual);
    }

    @Test
    void deleteResourceShouldNotBeRemoved() {
        final var id = 1L;
        final var expected = ResponseEntity.status(404).body("Recurso no encontrado.");

        when(service.deleteResource(id)).thenReturn(false);
        final var actual = controller.deleteResource(id);

        assertEquals(expected, actual);
    }

    @Test
    void getAllResources() {
        final var resource = new ResourceModel();
        final var listResource = List.of(resource);
        final var expected = ResponseEntity.ok(listResource);

        when(service.getAllResources()).thenReturn(listResource);

        final var actual = controller.getAllResources();
        assertEquals(expected, actual);
    }

    @Test
    void getResourceById() {
        final var id = 1L;
        final var resource = new ResourceModel();
        final var optionalResourceModel = Optional.of(resource);
        final var expected = ResponseEntity.ok(resource);

        when(service.getResourceById(id)).thenReturn(optionalResourceModel);

        final var actual = controller.getResourceById(id);

        assertEquals(expected, actual);
    }

    @Test
    void getResourceByIdNotFound() {
        final var id = 1L;
        final var optionalResourceModel = Optional.<ResourceModel>empty();
        final var expected = ResponseEntity.status(404).body(null);

        when(service.getResourceById(id)).thenReturn(optionalResourceModel);

        final var actual = controller.getResourceById(id);

        assertEquals(expected, actual);
    }

    @Test
    void getResourcesByCategory() {
        final var category = "";
        final var resource = new ResourceModel();
        final var listResource = List.of(resource);
        final var expected = ResponseEntity.ok(listResource);

        when(service.getResourcesByCategory(category)).thenReturn(listResource);
        final var actual = controller.getResourcesByCategory(category);
        assertEquals(expected, actual);

    }

    @Test
    void updateResource() {
        final var id = 1L;
        final ResourceDTO resourceDTO = new ResourceDTO();
        final var resource = new ResourceModel();

        final var expected = ResponseEntity.ok("Recurso actualizado con éxito");

        when(service.updateResource(id, resourceDTO)).thenReturn(Optional.of(resource));
        final var actual = controller.updateResource(id, resourceDTO);
        assertEquals(expected, actual);
    }

    @Test
    void updateResource_NotFound() {

        final var id = 1L;
        final ResourceDTO resourceDTO = new ResourceDTO();

        final var expected = ResponseEntity.status(404).body("Recurso no encontrado");

        when(service.updateResource(id, resourceDTO)).thenReturn(Optional.empty());


        final var actual = controller.updateResource(id, resourceDTO);

        assertEquals(expected, actual);
    }


    @Test
    void getFile() {
        final var filename = "filename";
        final var expected = ResponseEntity.notFound().build();

        when(service.getFile(filename)).thenReturn(ResponseEntity.notFound().build());

        final var actual = controller.getFile(filename);

        assertEquals(expected, actual);
    }
}