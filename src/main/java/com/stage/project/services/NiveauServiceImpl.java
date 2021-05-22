package com.stage.project.services;

import com.stage.project.Dao.NiveauRepository;
import com.stage.project.entities.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NiveauServiceImpl implements NiveauService {

    @Autowired
    NiveauRepository niveauRepository;

    @Override
    public List<Niveau> getAll() {
        return niveauRepository.findAll();
    }
}
