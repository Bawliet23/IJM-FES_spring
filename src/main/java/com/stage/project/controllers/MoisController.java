package com.stage.project.controllers;

import com.stage.project.Dao.MoisRepository;
import com.stage.project.entities.Mois;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mois")
public class MoisController {

    @Autowired
    MoisRepository moisRepository;

    @GetMapping("{annee}")
    public List<Mois> getMoisByAnnee(@PathVariable("annee") String annee){
        return moisRepository.findAllByAnnee_Nom(annee);
    }

}
