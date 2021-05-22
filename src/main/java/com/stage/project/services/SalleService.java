package com.stage.project.services;

import com.stage.project.entities.Salle;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface SalleService {
    Set<Salle>getSallesFreeInHoraire(String jour,String demiHeure);
    Page<Salle> getAll(Optional<String> nom, Optional<Integer> page);
    void add(Salle salle);
    void delete(String salle);
    List<Object> getHoraires(String salle);
    boolean isFree(String jour, String demiHeure, Long activite_id);
    List<Salle> getAllPages();
}
