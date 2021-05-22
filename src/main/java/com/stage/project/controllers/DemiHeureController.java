package com.stage.project.controllers;

import com.stage.project.entities.DemiHeure;
import com.stage.project.services.DemiHeureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demiHeurs")
public class DemiHeureController {

    @Autowired
    DemiHeureService demiHeureService;

    @GetMapping
    public List<DemiHeure>getAll(){
        return demiHeureService.getAll();
    }

}
