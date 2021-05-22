package com.stage.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.entities.Matiere;
import com.stage.project.entities.Niveau;
import com.stage.project.services.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/matieres")
public class MatiereController {

    @Autowired
    MatiereService matiereService;

    @GetMapping("/allpages")
    public List<Matiere>getAllPages(){
        return matiereService.getAllPages();
    }

    @GetMapping
    public Page<Matiere> getAll(@RequestParam Optional<String> nom, @RequestParam Optional<Integer>page){
        return matiereService.getAll(nom,page);
    }

    @GetMapping("{nom}")
    public Map<String,Object> get(@PathVariable("nom") String nom){
        return matiereService.getOne(nom);
    }

    @GetMapping(path="/photo/{nom}")
    public byte[] getPhoto(@PathVariable("nom") String nom) throws Exception{
        return matiereService.getImage(nom);
    }

    @PostMapping("{nom}/niveaux")
    public Matiere addNiveux(@PathVariable("nom") String nom, @RequestBody List<Niveau> niveaux){
        return matiereService.addNiveaux(nom,niveaux);
    }

    @PostMapping
    public Matiere store(@RequestParam("file") MultipartFile file,
			 @RequestParam("matiere") String matiere,@RequestParam("niveaux") String niveaux){
        try {
            return matiereService.add(file,matiere,niveaux);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PatchMapping("{nom}")
    public Matiere update(@PathVariable("nom") String nom,@RequestParam(value = "file", required = false) MultipartFile file ,
			@RequestParam("matiere") String matiere,@RequestParam("niveaux") String niveaux){
        try {
            return matiereService.update(nom,file,matiere,niveaux);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("{nom}")
    public boolean delete(@PathVariable("nom") String nom) {
        return matiereService.delete(nom);
    }

}
