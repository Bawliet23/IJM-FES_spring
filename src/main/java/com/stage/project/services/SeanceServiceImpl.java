package com.stage.project.services;

import com.stage.project.Dao.PreseneceRepository;
import com.stage.project.Dao.SeanceRepository;
import com.stage.project.entities.Presence;
import com.stage.project.entities.Seance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class SeanceServiceImpl implements SeanceService {

    @Autowired
    SeanceRepository seanceRepository;
    @Autowired
    PreseneceRepository preseneceRepository;

    @Override
    public Map<String,Object> getPresences(Long id) {
        Seance seance=seanceRepository.findFirstById(id);
        Map<String,Object>data=new HashMap<>();
        List<Presence>pre=preseneceRepository.findAllBySeanceAndAbsentEquals(seance,false);
        List<Presence>abs=preseneceRepository.findAllBySeanceAndAbsentEquals(seance,true);
        data.put("EtudiantPresent",pre);
        data.put("EtudiantsAbsent",abs);
        data.put("remarque",seance.getDescription());
        return data;
    }
}
