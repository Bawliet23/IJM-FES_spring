package com.stage.project.Dao;

import com.stage.project.entities.Matiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatiereRepository extends JpaRepository<Matiere,Long> {
    Matiere findFirstByNom(String nom);
    Page<Matiere>findByNomContaining(String nom,Pageable pageable);
    Page<Matiere> findAll(Pageable pageable);
}
