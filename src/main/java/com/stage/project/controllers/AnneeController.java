package com.stage.project.controllers;

import com.stage.project.entities.Annee;
import com.stage.project.services.AnneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/annees")
public class AnneeController {

    @Autowired
    AnneeService anneeService;

    @GetMapping
    public List<Annee>getAll(){
        return anneeService.getAll();
    }
}
