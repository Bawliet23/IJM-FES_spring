package com.stage.project.services;

import com.stage.project.entities.Data.PaimentData;
import com.stage.project.entities.Paiment;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PaimentService {
    Map<String,Object> getAll(Optional<String> annee,Optional<String> mois,Optional<Integer> page);
    Long add(PaimentData paimentData);
    Long update(PaimentData paimentData);
    void delete(Long id);
    Map<String,Object> getOne(Long id);
    Map<String,Object> paimentData(Paiment paiment);
}
