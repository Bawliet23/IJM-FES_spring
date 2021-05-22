package com.stage.project.services;

import com.stage.project.Dao.DemiHeureRepository;
import com.stage.project.entities.DemiHeure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class DemiHeureServiceImpl implements DemiHeureService {

    @Autowired
    DemiHeureRepository demiHeureRepository;

    @Override
    public List<DemiHeure> getAll() {
        return sortDemiHeurs(demiHeureRepository.findAll());
    }

    public List<DemiHeure>sortDemiHeurs(List<DemiHeure>demiHeures) {
        if (demiHeures.size()==0)return null;
        Collections.sort(demiHeures, new Comparator<DemiHeure>() {
            @Override
            public int compare(DemiHeure o1, DemiHeure o2) {

                if (o1.getHeure() > o2.getHeure()) {
                    return 1;
                } else if (o1.getHeure()  < o2.getHeure()) {
                    return -1;
                }
                ;
                if (o1.getMin() >o2.getMin()) {
                    return 1;
                } else if (o1.getMin() < o2.getMin()) {
                    return -1;
                }
                return 0;

            }
        });
        return demiHeures;
    }
}
