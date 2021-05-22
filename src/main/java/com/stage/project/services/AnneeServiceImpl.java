package com.stage.project.services;

import com.stage.project.Dao.AnneeRepository;
import com.stage.project.entities.Annee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnneeServiceImpl implements AnneeService {

    @Autowired
    AnneeRepository anneeRepository;

    @Override
    public List<Annee> getAll() {
        return anneeRepository.findAll();
    }
}
