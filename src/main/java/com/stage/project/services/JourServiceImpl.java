package com.stage.project.services;

import com.stage.project.Dao.JourRepository;
import com.stage.project.entities.Jour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class JourServiceImpl implements JourService {

    @Autowired
    JourRepository jourRepository;


    @Override
    public List<Jour> getAll() {
        return sortJours(jourRepository.findAll());
    }

    private List<Jour>sortJours(List<Jour>jours){
        if (jours.size()==0)return null;
        Collections.sort(jours, new Comparator<Jour>() {
            @Override
            public int compare(Jour o1, Jour o2) {

                if (jourToInt(o1.getNom()) > jourToInt(o2.getNom())) {
                    return 1;
                } else if (jourToInt(o1.getNom()) < jourToInt(o2.getNom())) {
                    return -1;
                }
                return 0;

            }
        });
        return jours;
    }

    private int jourToInt(String jour) {
        switch (jour) {
            case "lundi":
                return 1;
            case "mardi":
                return 2;
            case "mercredi":
                return 3;
            case "jeudi":
                return 4;
            case "vendredi":
                return 5;
            case "samedi":
                return 6;
            case "dimanche":
                return 7;
        }
        return 0;
    }
}
