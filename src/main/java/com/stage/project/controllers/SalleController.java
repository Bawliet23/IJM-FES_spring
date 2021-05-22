package com.stage.project.controllers;

import com.stage.project.entities.Matiere;
import com.stage.project.entities.Salle;
import com.stage.project.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/salles")
public class SalleController {

    @Autowired
    SalleService salleService;

    @GetMapping("/horaire")
    public Set<Salle> getProfFreeInHoraire (@RequestParam String jour, @RequestParam String demiHeure){
        return salleService.getSallesFreeInHoraire(jour,demiHeure);
    }

    @GetMapping("/isFree")
    public boolean isFree(@RequestParam String jour, @RequestParam String demiHeure,@RequestParam Long activite_id){
        return salleService.isFree(jour,demiHeure,activite_id);
    }
    @GetMapping("/allpages")
    public List<Salle>getAllPages(){
        return salleService.getAllPages();
    }
    @GetMapping
    public Page<Salle> index(@RequestParam Optional<String> nom, @RequestParam Optional<Integer>page){
        return salleService.getAll(nom,page);
    }

    @PostMapping
    public void add(@RequestBody Salle salle){
        salleService.add(salle);
    }

    @DeleteMapping("{salle}")
    public void deleteSalle(@PathVariable("salle") String salle){
        salleService.delete(salle);
    }

    @GetMapping("{salle}/horaires")
    public List<Object> getHoraires(@PathVariable("salle") String salle){
        return salleService.getHoraires(salle);
    }
}
