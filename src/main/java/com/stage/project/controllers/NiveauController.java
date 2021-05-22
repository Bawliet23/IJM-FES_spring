package com.stage.project.controllers;

import com.stage.project.entities.Niveau;
import com.stage.project.services.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/niveaux")
public class NiveauController {

    @Autowired
    NiveauService niveauService;

    @GetMapping
    public List<Niveau>getAll(){
        return niveauService.getAll();
    }

}
