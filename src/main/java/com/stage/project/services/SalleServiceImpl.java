package com.stage.project.services;

import com.stage.project.Dao.ActiviteRepository;
import com.stage.project.Dao.HorraireRepository;
import com.stage.project.Dao.SalleRepository;
import com.stage.project.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    @Autowired
    SalleRepository salleRepository;
    @Autowired
    HorraireRepository horraireRepository;
    @Autowired
    ActiviteRepository activiteRepository;

    @Override
    public Set<Salle> getSallesFreeInHoraire(String jour, String demiHeure) {
        Set<Salle>salles=new HashSet<>();
        salleRepository.findAll().forEach(salle -> {
            boolean d[]={false};
            salle.getActivites().forEach(activite -> {
                if (horraireRepository.findFirstByDemiHeureNomAndJourNomAndActiviteId(demiHeure,jour,activite.getId())!=null)d[0]=true;
            });
            if (d[0]==false)salles.add(salle);
        });
        return salles;
    }

    @Override
    public Page<Salle> getAll(Optional<String> nom, Optional<Integer> page) {
        if (nom.isPresent() && !nom.get().equals(""))return salleRepository.findByNomContaining(nom.get(), PageRequest.of(page.orElse(0), 10));
        else return salleRepository.findAll(PageRequest.of(page.orElse(0), 10));
    }
    @Override
    public List<Salle> getAllPages() {
       return salleRepository.findAll();
    }

    @Override
    public void add(Salle salle) {
        salleRepository.save(salle);
    }

    @Override
    public void delete(String nom) {
        Salle salle=salleRepository.findFirstByNom(nom);
        if (salle.getActivites().size()==0)salleRepository.delete(salle);
    }

    @Override
    public List<Object> getHoraires(String nom) {
        Salle salle=salleRepository.findFirstByNom(nom);
        List<Horraire>horraires=new ArrayList<>();
        salle.getActivites().forEach(activite -> {
            horraires.addAll(activite.getHorraires());
        });
        return horairesData(sortByJourAndDemiHeur(horraires));
    }

    @Override
    public boolean isFree(String jour, String demiHeure, Long activite_id) {
        Activite activite=activiteRepository.findFirstById(activite_id);
        List<Horraire>horraires=horraireRepository.findAllByDemiHeureNomAndJourNomAndActiviteIsNot(demiHeure,jour,activite);
        for (Horraire horraire : horraires) {
            if (horraire.getActivite().getSalle().getNom().equals(activite.getSalle().getNom())){
                return false;
            }
        }
        return true;
    }

    public List<Object>horairesData(List<Horraire>horraires){
        if (horraires==null)return null;
        List<Object>data=new ArrayList<>();
        Horraire prevHorraire =null;
        Map<String,Object>h=new HashMap<>();

        List<Object>jourHoraires=new ArrayList<>();
        Map<String,Object>horaireObj=new HashMap<>();

        h.put("jour", horraires.get(0).getJour().getNom());

        horaireObj.put("startHour", horraires.get(0).getDemiHeure().getNom());

        horaireObj.put("lastHour",null);
        if (horraires.get(0).getActivite()!=null)horaireObj.put("activite",horraires.get(0).getActivite().getNom());
        if (horraires.get(0).getProf()!=null)horaireObj.put("prof",horraires.get(0).getProf().getPrenom()+" "+horraires.get(0).getProf().getNom());

        prevHorraire=horraires.get(0);

        for (int i = 1; i < horraires.size(); i++) {
            Horraire horraire=horraires.get(i);
            System.out.println(horraire.getJour().getNom());
            if (horraire.getJour().equals(prevHorraire.getJour())) {
                if (prevHorraire.getActivite().getId().equals(horraires.get(i).getActivite().getId())) {
                    if (prevHorraire.getDemiHeure().getMin() + 30 == 60 && prevHorraire.getDemiHeure().getHeure() + 1 == horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin() == 0) {
                        horaireObj.put("lastHour", horraire.getDemiHeure().getNom());
                    } else if (prevHorraire.getDemiHeure().getMin() == 0 && prevHorraire.getDemiHeure().getHeure() == horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin() == 30) {
                        horaireObj.put("lastHour", horraire.getDemiHeure().getNom());
                    } else {
                        jourHoraires.add(horaireObj);
                        h = new HashMap<>();
                        horaireObj=new HashMap<>();
                        horaireObj.put("jour", horraire.getJour().getNom());
                        horaireObj.put("startHour", horraire.getDemiHeure().getNom());
                        if (horraires.get(i).getActivite() != null)
                            horaireObj.put("activite", horraires.get(i).getActivite().getNom());
                        if (horraires.get(i).getProf() != null)
                            horaireObj.put("prof", horraires.get(i).getProf().getPrenom() + " " + horraires.get(i).getProf().getNom());
                    }

                }
                else {
                    jourHoraires.add(horaireObj);
                    horaireObj=new HashMap<>();
                    h.put("jour", horraire.getJour().getNom());
                    horaireObj.put("startHour", horraire.getDemiHeure().getNom());
                    if (horraires.get(i).getActivite() != null)
                        horaireObj.put("activite", horraires.get(i).getActivite().getNom());
                    if (horraires.get(i).getProf() != null)
                        horaireObj.put("prof", horraires.get(i).getProf().getPrenom() + " " + horraires.get(i).getProf().getNom());
                }
            }
            else{
                jourHoraires.add(horaireObj);
                h.put("horaires",jourHoraires);
                data.add(h);
                h=new HashMap<>();
                horaireObj=new HashMap<>();
                jourHoraires=new ArrayList<>();
                h.put("jour", horraire.getJour().getNom());
                horaireObj.put("startHour", horraire.getDemiHeure().getNom());
                if (horraires.get(i).getActivite() != null)
                    horaireObj.put("activite", horraires.get(i).getActivite().getNom());
                if (horraires.get(i).getProf() != null)
                    horaireObj.put("prof", horraires.get(i).getProf().getPrenom() + " " + horraires.get(i).getProf().getNom());
            }
            if (i==horraires.size()-1){
                jourHoraires.add(horaireObj);
                h.put("horaires",jourHoraires);
                data.add(h);
            }
            prevHorraire =horraire;
        }
        return data;
    }

    public List<Horraire> sortByJourAndDemiHeur(List<Horraire> horraires) {
        if (horraires.size()==0)return null;
        Collections.sort(horraires, new Comparator<Horraire>() {
            @Override
            public int compare(Horraire o1, Horraire o2) {
                Jour jour_a = o1.getJour();
                Jour jour_b = o2.getJour();
                if (jourToInt(jour_a.getNom()) > jourToInt(jour_b.getNom())) {
                    return 1;
                } else if (jourToInt(jour_a.getNom()) < jourToInt(jour_b.getNom())) {
                    return -1;
                }

                DemiHeure demiHeure_a = o1.getDemiHeure();
                DemiHeure demiHeure_b = o2.getDemiHeure();
                String demiHeure_heure_a = demiHeure_a.getNom().substring(0, 2);
                demiHeure_heure_a = demiHeure_heure_a.replaceAll("\\D", "");
                String demiHeure_heure_b = demiHeure_b.getNom().substring(0, 2);
                demiHeure_heure_b = demiHeure_heure_b.replaceAll("\\D", "");
                if (Integer.parseInt(demiHeure_heure_a) > Integer.parseInt(demiHeure_heure_b)) {
                    return 1;
                } else if (Integer.parseInt(demiHeure_heure_a) < Integer.parseInt(demiHeure_heure_b)) {
                    return -1;
                }

                String demiHeure_min_a = demiHeure_a.getNom().substring(demiHeure_a.getNom().length() - 2);
                ;
                String demiHeure_min_b = demiHeure_b.getNom().substring(demiHeure_b.getNom().length() - 2);
                ;
                if (Integer.parseInt(demiHeure_min_a) > Integer.parseInt(demiHeure_min_b)) {
                    return 1;
                } else if (Integer.parseInt(demiHeure_min_a) < Integer.parseInt(demiHeure_min_b)) {
                    return -1;
                }
                return 0;

            }
        });
        return horraires;
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
