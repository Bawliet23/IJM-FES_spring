package com.stage.project.services;

import com.stage.project.Dao.*;
import com.stage.project.entities.Activite;
import com.stage.project.entities.Paiment;
import com.stage.project.entities.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class HomeServiceImpl implements HomeService {

    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    ActiviteRepository activiteRepository;
    @Autowired
    PaimentRepository paimentRepository;
    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    SalleRepository salleRepository;
    @Autowired
    HorraireRepository horraireRepository;
    @Autowired
    AnneeRepository anneeRepository;

    @Override
    public Map<String,Long> getStats() {
        Map<String,Long>data=new HashMap<>();
        data.put("etudiants",etudiantRepository.count());
        data.put("activites",activiteRepository.count());
        data.put("paiements",paimentRepository.count());
        data.put("matieres",matiereRepository.count());
        return data;
    }

    @Override
    public Map<String,Set> getSalles() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE",Locale.FRENCH);
        String jour=simpleDateFormat.format(date).toLowerCase();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.add(Calendar.HOUR,1);
        String demiHeure=dateFormat.format(cal.getTime());
        if(Integer.valueOf(demiHeure.substring(3))<30)
            demiHeure=demiHeure.substring(0,3)+"00";
        else
            demiHeure=demiHeure.substring(0,3)+"30";

        Map<String,Set>data=new HashMap<>();
        Set<Salle>sallesLibres=new HashSet<>();
        Set<Map>sallesOccupees=new HashSet<>();
        System.out.println(demiHeure+" ----- "+jour);
        String finalDemiHeure = demiHeure;
        salleRepository.findAll().forEach(salle -> {
            boolean d[]={false};
            Activite []act={null};
            salle.getActivites().forEach(activite -> {
                if (horraireRepository.findFirstByDemiHeureNomAndJourNomAndActiviteId(finalDemiHeure,jour,activite.getId())!=null){
                    act[0]=activite;
                    d[0]=true;
                }
            });
            if (d[0]==false)sallesLibres.add(salle);
            else {
                Map<String,Object>sl=new HashMap<>();
                sl.put("nom",salle.getNom());
                sl.put("activite",act[0].getId());
                sallesOccupees.add(sl);
            }
        });
        data.put("sallesLibres",sallesLibres);
        data.put("sallesOccupees",sallesOccupees);
        return data;
    }

    @Override
    public Map<String, Object> getCaiss() {
        List<String>annees=new ArrayList<>();
        List<Float>nbr=new ArrayList<>();
        anneeRepository.findAll().forEach(annee -> {
            List<Paiment>paiments=new ArrayList<>();
            annees.add(annee.getNom());
            float []count={0};
            annee.getMois().forEach(mois -> {
                mois.getPaiments().forEach(paiment -> {
                    if (!paiments.contains(paiment)) count[0]+=paiment.getMontantPayee();
                    paiments.add(paiment);
                });
            });
            nbr.add(count[0]);
        });
        Map<String,Object>data=new HashMap<>();
        data.put("annees",annees);
        data.put("nbr",nbr);
        return data;
    }
}
