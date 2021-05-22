package com.stage.project.services;

import com.stage.project.entities.Data.HorraireData;
import com.stage.project.entities.Data.MatiereData;
import com.stage.project.entities.Horraire;
import com.stage.project.entities.Prof;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ProfService {
    Map<String,Object> getAll(Optional<String> query, Optional<Integer>page);
    ResponseEntity<String> add(Prof prof);
    Prof update(Prof prof);
    boolean delete(String cin);
    void addMatiere(String cin, MatiereData matiereData);
    void deleteMatiere(String cin,String matiereNom);
    void deleteNiveauMatiere(String cin,String idNiveauMatiere);
    void addHoraireToProf(String cin, Horraire horraire);
    void deleteHoraireToProf(String cin,String idHoraire);
    Map<String,Object> getOne(String cin);
    List<Object> getHorairesByJour(String cin, String jour);
    Map<String,Object> profData(Prof prof);
    Set<Prof> getProfFreeInHoraire(Long activite_id,String jour,String demiHeure);
    List<Object>getAllHoraires(String cin);
}
