package com.BootCamp.Backend.ProyectoBackend.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BootCamp.Backend.ProyectoBackend.models.User;

@Repository  
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
