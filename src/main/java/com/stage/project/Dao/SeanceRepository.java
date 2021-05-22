package com.stage.project.Dao;

import com.stage.project.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance,Long> {
    Seance findFirstById(Long id);
}
