package com.stage.project.Dao;

import com.stage.project.entities.Mois;
import com.stage.project.entities.Paiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PaimentRepository extends JpaRepository<Paiment,Long> {
    Paiment findFirstById(Long id);
    Page<Paiment> findAllByMois(Mois mois,Pageable pageable);
    List<Paiment>findAllByActiviteIdAndEtudiantId(Long etudiant_id,Long activite_id);
    Page<Paiment> findAll(Pageable pageable);
}
