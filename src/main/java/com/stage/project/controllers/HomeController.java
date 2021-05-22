package com.stage.project.controllers;

import com.stage.project.entities.Salle;
import com.stage.project.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/stats")
    public Map<String,Long>getStats(){
        return homeService.getStats();
    }

    @GetMapping("/salles")
    public Map<String,Set> getSalles(){
        return homeService.getSalles();
    }

    @GetMapping("/caisse")
    public Map<String,Object>getCaisse(){
        return homeService.getCaiss();
    }
}
