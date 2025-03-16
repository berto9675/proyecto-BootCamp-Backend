package com.BootCamp.Backend.ProyectoBackend.service;

import com.BootCamp.Backend.ProyectoBackend.models.User;
import com.BootCamp.Backend.ProyectoBackend.repositories.UserRepository;
import com.BootCamp.Backend.ProyectoBackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por su ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Guardar un usuario
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Eliminar un usuario por su ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }
}
