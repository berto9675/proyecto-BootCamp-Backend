package com.BootCamp.Backend.ProyectoBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.BootCamp.Backend.ProyectoBackend.models.ResourceModel;

public interface ResourceRepository extends JpaRepository<ResourceModel, Long>{
    List<ResourceModel> findByCategory(String category);
}