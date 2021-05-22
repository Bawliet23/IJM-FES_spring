package com.stage.project.controllers;

import com.stage.project.entities.User;
import com.stage.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<User>findAll(@RequestParam Optional<String> role, @RequestParam Optional<Integer>page){
        return userService.findAll(role,page);
    }

    @GetMapping("/password")
    public ResponseEntity<String> getPassword(@RequestParam String type,@RequestParam String id){
        return userService.getPassword(type,id);
    }


}
