package com.stage.project.Dao;

import com.stage.project.entities.Presence;
import com.stage.project.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreseneceRepository extends JpaRepository<Presence,Long> {
    List<Presence>findAllBySeanceAndAbsentEquals(Seance seance,boolean absent);
}
