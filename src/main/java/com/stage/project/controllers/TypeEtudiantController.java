package com.stage.project.controllers;

import com.stage.project.Dao.TypeEtudiantRepository;
import com.stage.project.entities.TypeEtudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/TypesEtudiant")
public class TypeEtudiantController {

    @Autowired
    TypeEtudiantRepository typeEtudiantRepository;

    @GetMapping
    public List<TypeEtudiant> getAll(){
        return typeEtudiantRepository.findAll();
    }
}
