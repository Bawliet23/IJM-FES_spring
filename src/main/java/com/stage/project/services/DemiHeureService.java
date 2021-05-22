package com.stage.project.services;

import com.stage.project.entities.DemiHeure;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface DemiHeureService {
    List<DemiHeure>getAll();
}
