package com.stage.project.Dao;

import com.stage.project.entities.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiveauRepository extends JpaRepository<Niveau,Long> {
    Niveau findFirstByNv(int nv);
}
