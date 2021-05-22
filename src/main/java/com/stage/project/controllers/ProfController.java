package com.stage.project.controllers;

import com.stage.project.entities.Data.HorraireData;
import com.stage.project.entities.Data.MatiereData;
import com.stage.project.entities.Horraire;
import com.stage.project.entities.Prof;
import com.stage.project.services.ProfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/professeurs")
public class ProfController {

    @Autowired
    ProfService profService;

    @GetMapping
    public Map<String,Object> index(@RequestParam Optional<String> query, @RequestParam Optional<Integer>page) {
        return profService.getAll(query,page);
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestBody Prof prof) {
        return profService.add(prof);
    }

    @PutMapping("{cin}")
    public Prof update(@PathVariable("cin") String cin,@RequestBody Prof prof) {
        return profService.update(prof);
    }

    @DeleteMapping("{cin}")
    public boolean delete(@PathVariable("cin") String cin) {
        return profService.delete(cin);
    }

    @GetMapping("{cin}")
    public Map<String,Object> get(@PathVariable("cin") String cin) {
        return profService.getOne(cin);
    }

    @GetMapping("{cin}/horaires/{jour}")
    public List<Object> getHorairesByJour(@PathVariable("cin") String cin, @PathVariable("jour") String jour){
        return profService.getHorairesByJour(cin,jour);
    }

    @PostMapping("{cin}/matiere")
    public void addNiveauMatiereToProf(@PathVariable("cin") String cin, @RequestBody MatiereData matiereData)
    {
        profService.addMatiere(cin, matiereData);
    }

    @DeleteMapping("{cin}/matiere/{matiere}")
    public void deleteMatiere(@PathVariable("cin") String cin,@PathVariable("matiere") String matiereNom){
        profService.deleteMatiere(cin,matiereNom);
    }

    @DeleteMapping("{cin}/niveaumatiere/{idNiveauMatiere}")
    public void deleteNiveauMatiereToProf(@PathVariable("cin") String cin, @PathVariable("idNiveauMatiere") String idNiveauMatiere)
    {
        profService.deleteNiveauMatiere(cin, idNiveauMatiere);
    }

    @PostMapping("{cin}/horaire")
    public void addHoraireToProf(@PathVariable("cin") String cin, @RequestBody Horraire horraire)
    {
        profService.addHoraireToProf(cin, horraire);
    }

    @DeleteMapping("{cin}/horaire/{idHoraire}")
    public void deleteHoraireToProf(@PathVariable("cin") String cin, @PathVariable("idHoraire") String idHoraire) {
        profService.deleteHoraireToProf(cin, idHoraire);
    }

    @GetMapping("{cin}/horaires")
    public List<Object>getAllHoraires(@PathVariable("cin") String cin){
        return profService.getAllHoraires(cin);
    }

    @GetMapping("/horaire")
    public Set<Prof> getProfFreeInHoraire (@RequestParam Long activite_id,@RequestParam String jour,@RequestParam String demiHeure){
        return profService.getProfFreeInHoraire(activite_id,jour,demiHeure);
    }

}
