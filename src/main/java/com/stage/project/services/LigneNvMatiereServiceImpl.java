package com.stage.project.services;

import com.stage.project.Dao.LignNvMatiereRepository;
import com.stage.project.Dao.MatiereRepository;
import com.stage.project.Dao.NiveauRepository;
import com.stage.project.entities.LigneNvMatiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LigneNvMatiereServiceImpl implements LigneNvMatiereService {

    @Autowired
    LignNvMatiereRepository lignNvMatiereRepository;
    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    NiveauRepository niveauRepository;

    @Override
    public Boolean delete(String id) {
        LigneNvMatiere ligneNvMatiere=lignNvMatiereRepository.findFirstById(id);
        if (ligneNvMatiere.getActivites().isEmpty()){
            lignNvMatiereRepository.delete(ligneNvMatiere);
            return true;
        }
        return false;
    }
}
