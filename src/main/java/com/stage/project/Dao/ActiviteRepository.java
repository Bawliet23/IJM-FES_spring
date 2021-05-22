package com.stage.project.Dao;

import com.stage.project.entities.Activite;
import com.stage.project.entities.Groupe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActiviteRepository extends JpaRepository<Activite,Long> {
    Activite findFirstById(Long id);
    List<Activite>findAllByGratuit(boolean d);
    List<Activite>findAllByTypeEtudiantNom(String typeEtudiant_nom);
    Page<Activite>findByNomContaining(String nom,Pageable pageable);
    Page<Activite> findAll(Pageable pageable);
}
