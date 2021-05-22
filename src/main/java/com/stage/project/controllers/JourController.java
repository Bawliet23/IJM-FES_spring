package com.stage.project.controllers;

import com.stage.project.entities.Jour;
import com.stage.project.services.JourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jours")
public class JourController {

    @Autowired
    JourService jourService;

    @GetMapping
    public List<Jour>getAll(){
        return jourService.getAll();
    }

}
