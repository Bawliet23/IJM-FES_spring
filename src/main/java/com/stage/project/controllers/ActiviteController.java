package com.stage.project.controllers;

import com.stage.project.entities.*;
import com.stage.project.entities.Data.ActiviteData;
import com.stage.project.services.ActiviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/activites")
public class ActiviteController {


   private final ActiviteService activiteService;

    public ActiviteController(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

    @GetMapping
    public Map<String,Object> index(@RequestParam Optional<String> nom, @RequestParam Optional<Integer>page){
        return activiteService.getAll(nom,page);
    }

    @GetMapping("/lessInofs")
    public List<Object> lessInfos(){
        return activiteService.lessInofs();
    }

    @GetMapping("{etudiant_id}/{typeEtudiant}/{type}")
    public List<Object>getAllByTypeAndTypeEtudiant(@PathVariable("etudiant_id")Long etudiant_id,@PathVariable("typeEtudiant") String typeEtudiant,@PathVariable("type") String type){
        return activiteService.getByTypeEtudiantAndType(etudiant_id,typeEtudiant,type);
    }

    @PostMapping
    public Long store(@RequestBody ActiviteData activiteData){
        return activiteService.add(activiteData);
    }

    @PatchMapping("{id}")
    public Long update(@PathVariable("id") Long id,@RequestBody ActiviteData activiteData){
        return activiteService.update(activiteData);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        activiteService.delete(id);
    }

    @GetMapping("{id}")
    public Map<String,Object> get(@PathVariable("id") Long id){
        return activiteService.getOne(id);
    }

    @GetMapping("{id}/horaires")
    public ArrayList<Object> getHoraires(@PathVariable("id") Long id){
        return activiteService.getHoraires(id);
    }

    @PostMapping("{id}/etudiants")
    public ResponseEntity<String> addEtudiant(@PathVariable("id") Long id,@RequestParam Long idEtudiant){
        return activiteService.addEtudiant(id,idEtudiant);
    }

    @DeleteMapping("{id}/etudiants/{idEtudiant}")
    public void deleteEtudiant(@PathVariable("id") Long id,@PathVariable("idEtudiant") Long idEtudiant){
        activiteService.removeEtudiant(id,idEtudiant);
    }

    @GetMapping("{id}/etudiants")
    public Collection<Etudiant> getEtudiants(@PathVariable("id") Long id){
         return activiteService.getEtudiants(id);
    }

    @PostMapping("{id}/horaire")
    public void addHoraireToProf(@PathVariable("id") Long id, @RequestBody Horraire horraire)
    {
        activiteService.addHoraire(id, horraire);
    }

    @DeleteMapping("{id}/horaire/{horaire_id}")
    public void deleteHoraire(@PathVariable("id") Long id,@PathVariable("horaire_id") String horaire_id){
        activiteService.deleteHoraire(id,horaire_id);
    }

    @PostMapping("{id}/prof")
    public void addProf(@PathVariable("id") Long id, @RequestParam("horaire_id") String horaire_id,
                                @RequestParam("cin") String cin)
    {
        activiteService.addProf(id,horaire_id,cin);
    }

    @GetMapping("{id}/seances")
    public List<Map>getSeances(@PathVariable("id") Long id){
        return activiteService.getSeances(id);
    }

}
