package com.stage.project.services;

import com.stage.project.entities.Salle;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HomeService {
    Map<String,Long>getStats();
    Map<String,Set> getSalles();
    Map<String, Object> getCaiss();
}
