package com.stage.project.services;

import com.stage.project.entities.*;
import com.stage.project.entities.Data.ActiviteData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

public interface ActiviteService {
    Map<String,Object> getAll(Optional<String>nom, Optional<Integer> page);
    List<Object>lessInofs();
    List<Object>getByTypeEtudiantAndType(Long etudiant_id,String typeEtudiant_nom,String type);
    Long add(ActiviteData activiteData);
    Long update(ActiviteData activiteData);
    void delete(Long id);
    ResponseEntity<String> addEtudiant(Long id, Long etudiantId);
    void removeEtudiant(Long id,Long etudiantId);
    Collection<Etudiant> getEtudiants(Long id);
    Map<String,Object> getOne(Long id);
    Map<String,Object> activitesData(Activite activite);
    void addHoraire(Long id, Horraire horraire);
    void deleteHoraire(Long id, String horaire_id);
    void addProf(Long id, String horaire_id, String cin);
    ArrayList<Object> getHoraires(Long id);
    List<Map> getSeances(Long id);
}
