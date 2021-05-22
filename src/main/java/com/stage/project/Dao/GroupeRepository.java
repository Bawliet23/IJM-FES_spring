package com.stage.project.Dao;

import com.stage.project.entities.Etudiant;
import com.stage.project.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    Groupe findFirstById(Long id);
    List<Groupe>findAllByTypeEtudiantNomAndEtudiantsNotContaining(String typeEtudiant_nom, Etudiant etudiant);
    List<Groupe>findAllByEtudiantsNotContaining(Etudiant etudiant);
}
