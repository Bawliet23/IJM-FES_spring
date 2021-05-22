package com.stage.project.controllers;

import com.stage.project.services.LigneNvMatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/LignNvMatieres")
public class LigneNvMatiereController {

    @Autowired
    LigneNvMatiereService ligneNvMatiereService;

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable("id") String id) {
        return ligneNvMatiereService.delete(id);
    }

}
