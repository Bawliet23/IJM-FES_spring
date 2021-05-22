package com.stage.project.controllers;

import com.stage.project.entities.Presence;
import com.stage.project.services.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/seances")
public class SeanceController {

    @Autowired
    SeanceService seanceService;

    @GetMapping("{id}/presences")
    public Map<String, Object> getPresnes(@PathVariable("id") Long id){
        return seanceService.getPresences(id);
    }

}
