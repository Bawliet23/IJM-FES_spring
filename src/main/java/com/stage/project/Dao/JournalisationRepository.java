package com.stage.project.Dao;

import com.stage.project.entities.Journalisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalisationRepository extends JpaRepository<Journalisation,Long> {
}
