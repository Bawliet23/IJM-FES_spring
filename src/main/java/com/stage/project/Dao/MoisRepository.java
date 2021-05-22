package com.stage.project.Dao;

import com.stage.project.entities.Mois;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoisRepository extends JpaRepository<Mois,Long> {
    Mois findFirstByNom(String nom);
    List<Mois> findAllByAnnee_Nom(String nom);
}
