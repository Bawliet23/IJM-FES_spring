package com.stage.project.Dao;

import com.stage.project.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle,Long> {
    Salle findFirstByNom(String nom);
    Page<Salle> findByNomContaining(String nom, Pageable pageable);
    Page<Salle> findAll(Pageable pageable);
    void deleteByNom(String nom);
}
