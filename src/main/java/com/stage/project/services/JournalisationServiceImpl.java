package com.stage.project.services;

import com.stage.project.Dao.JournalisationRepository;
import com.stage.project.entities.Journalisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JournalisationServiceImpl implements JournalisationService {

    @Autowired
    JournalisationRepository journalisationRepository;

    @Override
    public void add(Journalisation journalisation) {
        journalisationRepository.save(journalisation);
    }
}
