package com.stage.project.services;

import com.stage.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User findUserByUsername(String username);
    Page<User>findAll(Optional<String> role,Optional<Integer>page);
    ResponseEntity<String> getPassword(String nom,String id);
}
