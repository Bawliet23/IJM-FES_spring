package com.stage.project.Dao;

import com.stage.project.entities.Horraire;
import com.stage.project.entities.Prof;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfRepository extends JpaRepository<Prof,Long> {
    Prof findFirstByCin(String cin);
    void deleteByCin(String cin);
    Page<Prof>findAll(Pageable pageable);
    Page<Prof> findByPrenomContainingOrNomContaining(String prenom, String nom, Pageable pageable);
    List<Prof>findAllByHorrairesContaining(Horraire horraire);
}
