package com.stage.project.Dao;

import com.stage.project.entities.Etudiant;
import com.stage.project.entities.Individu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndividuRepository extends JpaRepository<Individu,Long> {
    Individu findFirstById(Long id);
    List<Individu> findAllByTypeEtudiantNomAndEtudiantNot(String typeEtudiant_nom, Etudiant etudiant);
    List<Individu> findAll();
}
