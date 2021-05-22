package com.stage.project.services;

import com.stage.project.Dao.*;
import com.stage.project.config.RandomString;
import com.stage.project.entities.*;
import com.stage.project.entities.Data.HorraireData;
import com.stage.project.entities.Data.MatiereData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProfServiceImpl implements ProfService {

    @Autowired
    ProfRepository profRepository;
    @Autowired
    LignNvMatiereRepository lignNvMatiereRepository;
    @Autowired
    HorraireRepository horraireRepository;
    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    ActiviteRepository activiteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Map<String,Object> getAll(Optional<String>query,Optional<Integer>page) {
        List<Object> profs = new ArrayList<>();
        Page<Prof> pageProf;
        if (query.isPresent() && !query.get().equals(""))pageProf=profRepository.findByPrenomContainingOrNomContaining(query.orElse("_"),query.orElse("_"), PageRequest.of(page.orElse(0), 10));
        else pageProf=profRepository.findAll(PageRequest.of(page.orElse(0), 10));
        pageProf.forEach(prof -> profs.add(profData(prof)));

        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pageProf.getTotalPages());
        pageInfos.put("profs",profs);
        return pageInfos;
    }

    @Override
    public ResponseEntity<String> add(Prof prof) {
        Role role=roleRepository.findFirstByRoleName("ROLE_PROF");
        String passKey= RandomString.getAlphaNumericString(6);
        String password=passwordEncoder.encode(passKey);
        User user=new User(prof.getPrenom(),prof.getNom(),prof.getCin(),password,passKey,prof.getEmail());
        user.setProf(prof);
        user.setRole(role);

        profRepository.save(prof);
        userRepository.save(user);
        ResponseEntity<String> responseEntity= new ResponseEntity<>(passKey,HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public Prof update(Prof prof) {
        return profRepository.save(prof);
    }

    @Override
    public boolean delete(String cin) {
        Prof prof = profRepository.findFirstByCin(cin);
        if (prof.getLigneNvMatieres().size() == 0 && prof.getHorraires().size() == 0) {
            profRepository.delete(prof);
            return true;
        }
        return false;
    }

    @Override
    public void addMatiere(String cin, MatiereData matiereData) {
        Prof prof = profRepository.findFirstByCin(cin);
        matiereData.getNiveaux().forEach((niveau -> {
            LigneNvMatiere ligneNvMatiere = lignNvMatiereRepository.findFirstByMatiereNomAndNiveauNv(matiereData.getNom(), niveau.getNv());
            if (!prof.getLigneNvMatieres().contains(ligneNvMatiere)){
                prof.getLigneNvMatieres().add(ligneNvMatiere);
                ligneNvMatiere.getProfs().add(prof);
            }

        }));
        profRepository.save(prof);
    }

    @Override
    public void deleteMatiere(String cin, String matiereNom) {
        Prof prof = profRepository.findFirstByCin(cin);
        Matiere matiere = matiereRepository.findFirstByNom(matiereNom);
        matiere.getLigneNvsMatieres().forEach(ligneNvMatiere -> {
            prof.getLigneNvMatieres().remove(ligneNvMatiere);
            ligneNvMatiere.getProfs().remove(prof);
        });
        profRepository.save(prof);
    }

    @Override
    public Map<String, Object> getOne(String cin) {
        return profData(profRepository.findFirstByCin(cin));
    }

    @Override
    public void deleteNiveauMatiere(String cin, String idNiveauMatiere) {
        LigneNvMatiere ligneNvMatiere = lignNvMatiereRepository.findFirstById(idNiveauMatiere);
        Prof prof = profRepository.findFirstByCin(cin);
        ligneNvMatiere.getProfs().remove(prof);
        prof.getLigneNvMatieres().remove(ligneNvMatiere);
        profRepository.save(prof);
    }

    @Override
    public void addHoraireToProf(String cin, Horraire horraire) {
        Prof prof=profRepository.findFirstByCin(cin);
        Horraire horraire1=new Horraire();
        horraire1.setId(cin+""+horraire.getJour().getNom()+""+horraire.getDemiHeure().getNom());
        horraire1.setJour(horraire.getJour());
        horraire1.setDemiHeure(horraire.getDemiHeure());
        horraire1.setProf(prof);
        horraireRepository.save(horraire1);
    }

    @Override
    public void deleteHoraireToProf(String cin, String idHoraire) {
        String jour_id=idHoraire.split(",")[0];
        String demiHeure=idHoraire.split(",")[1];
        Horraire horraire = horraireRepository.findFirstByDemiHeureNomAndJourNomAndProfCin(demiHeure,jour_id,cin);
        if (horraire.getActivite()==null){
            horraireRepository.delete(horraire);
            return;
        }
        Prof prof = profRepository.findFirstByCin(cin);
        horraire.setProf(null);
        prof.getHorraires().remove(horraire);
        horraireRepository.save(horraire);
    }

    @Override
    public Map<String, Object> profData(Prof prof) {

        Map<String, Object> data = new HashMap<>();
        if (prof == null) return data;


        Set<Object> matieres = new HashSet<>();
        prof.getLigneNvMatieres().forEach((ligneNvMatiere) -> {

                Map<String, Object> matiere = new HashMap<>();
                matiere.put("nom", ligneNvMatiere.getMatiere().getNom());
                List<Niveau> niveaux = new ArrayList<>();
                ligneNvMatiere.getMatiere().getLigneNvsMatieres().forEach(ligneNvMatiere1 -> {
                    if (prof.getLigneNvMatieres().contains(ligneNvMatiere1)) niveaux.add(ligneNvMatiere1.getNiveau());
                });
                Collections.sort(niveaux, new Comparator<Niveau>() {
                    @Override
                    public int compare(Niveau o1, Niveau o2) {
                        if (o1.getNv()>o2.getNv())return 1;
                        else if (o1.getNv()<o2.getNv())return -1;
                        return 0;
                    }
                });
                matiere.put("niveaux", niveaux);
                matieres.add(matiere);

        });

        data.put("matieres", matieres);

        List<Jour>jours=new ArrayList<>();
        prof.getHorraires().forEach((horraire -> jours.add(horraire.getJour())));
        Collections.sort(jours, new Comparator<Jour>() {
                    @Override
                    public int compare(Jour o1, Jour o2) {
                        if (jourToInt(o1.getNom())>jourToInt(o2.getNom()))return 1;
                        else if (jourToInt(o1.getNom())<jourToInt(o2.getNom())) return -1;
                        return 0;
                    }
                });
        Set<Jour> set = new LinkedHashSet<>(jours);
        data.put("jours", set);

        data.put("cin", prof.getCin());
        data.put("nom", prof.getNom());
        data.put("prenom", prof.getPrenom());
        data.put("age", prof.getAge());
        data.put("dateNaissance", prof.getDateNaissance());
        data.put("adresse", prof.getAdresse());
        data.put("tele", prof.getTele());
        data.put("email", prof.getEmail());
        data.put("diplomes", prof.getDiplomes());

        return data;

    }

    @Override
    public Set<Prof> getProfFreeInHoraire(Long activite_id,String jour,String demiHeure) {
        Activite activite=activiteRepository.findFirstById(activite_id);
        Set<Prof>profs=new HashSet<>();
        horraireRepository.findByDemiHeureNomAndJourNom(demiHeure,jour).forEach(horraire -> {
            if (horraire.getActivite()==null && horraire.getProf().getLigneNvMatieres().contains(activite.getLigneNvMatiere()))profs.add(horraire.getProf());
        });
        return profs;
    }

    @Override
    public List<Object> getAllHoraires(String cin) {
        Prof prof=profRepository.findFirstByCin(cin);
        List<Object>data=new ArrayList<>();
        prof.getHorraires().forEach(horraire ->{
            HashMap<String,Object>h=new HashMap<>();
            if (horraire.getActivite()==null){
                h.put("jour",horraire.getJour().getNom());
                h.put("demiHeure",horraire.getDemiHeure().getNom());
                h.put("attribue",false);
                data.add(h);
            }else{
                h.put("jour",horraire.getJour().getNom());
                h.put("demiHeure",horraire.getDemiHeure().getNom());
                h.put("attribue",true);
                h.put("activite",horraire.getActivite().getNom());
                data.add(h);
            }
        });

        return data;
    }

    public List<Object>getHorairesByJour(String cin,String jour){
        List<Horraire>list=horraireRepository.findAllByProfCinAndJourNom(cin,jour);
        List<Horraire>horraires= sortByDemiHeur(list);

        List<Object>data=new ArrayList<>();
        Map<String,Object>h=new HashMap<>();

        h.put("startHour", horraires.get(0).getDemiHeure().getNom());
        if (horraires.get(0).getActivite()!=null) {
            h.put("activite", horraires.get(0).getActivite().getNom());
            h.put("matiere", horraires.get(0).getActivite().getLigneNvMatiere().getMatiere().getNom());
            h.put("niveau", horraires.get(0).getActivite().getLigneNvMatiere().getNiveau().getNv());
        }
        h.put("lastHour",null);

        Horraire prevHorraire=horraires.get(0);
        for (int i = 1; i < horraires.size(); i++) {
            Horraire horraire=horraires.get(i);
            System.out.println(horraire.getDemiHeure().getNom()+"ssssssss");
            if (horraire.getActivite()==null && prevHorraire.getActivite()==null ){
                if (prevHorraire.getDemiHeure().getMin()+30==60 && prevHorraire.getDemiHeure().getHeure()+1==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==0){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else if (prevHorraire.getDemiHeure().getMin()==0 && prevHorraire.getDemiHeure().getHeure()==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==30){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else {
                        data.add(h);
                        System.out.println(data.toString());
                        h=new HashMap<>();
                        h.put("startHour", horraire.getDemiHeure().getNom());
                        if (horraires.get(0).getActivite()!=null) {
                            h.put("activite", horraires.get(0).getActivite().getNom());
                            h.put("matiere", horraires.get(0).getActivite().getLigneNvMatiere().getMatiere().getNom());
                            h.put("niveau", horraires.get(0).getActivite().getLigneNvMatiere().getNiveau().getNv());
                        }
                    }
            }
            else if ( (horraire.getActivite()!=null && prevHorraire.getActivite()!=null) && (horraire.getActivite().getId().equals(prevHorraire.getActivite().getId())) ){
                if (prevHorraire.getDemiHeure().getMin()+30==60 && prevHorraire.getDemiHeure().getHeure()+1==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==0){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else if (prevHorraire.getDemiHeure().getMin()==0 && prevHorraire.getDemiHeure().getHeure()==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==30){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else {
                    data.add(h);
                    h=new HashMap<>();
                    h.put("startHour", horraire.getDemiHeure().getNom());
                    if (horraires.get(i).getActivite()!=null) {
                        h.put("activite", horraires.get(i).getActivite().getNom());
                        h.put("matiere", horraires.get(i).getActivite().getLigneNvMatiere().getMatiere().getNom());
                        h.put("niveau", horraires.get(i).getActivite().getLigneNvMatiere().getNiveau().getNv());
                    }
                }
            }
            else{
                System.out.println("mmm");
                data.add(h);
                h=new HashMap<>();
                h.put("startHour", horraire.getDemiHeure().getNom());
                if (horraires.get(i).getActivite()!=null) {
                    h.put("activite", horraires.get(i).getActivite().getNom());
                    h.put("matiere", horraires.get(i).getActivite().getLigneNvMatiere().getMatiere().getNom());
                    h.put("niveau", horraires.get(i).getActivite().getLigneNvMatiere().getNiveau().getNv());
                }
               // if (i==horraires.size()-1)data.add(h);
            }
            prevHorraire =horraire;
        }
        data.add(h);
        return data;
    }

    public List<Horraire> sortByJourAndDemiHeur(Set<Horraire> horraires) {
        if (horraires.size()==0)return null;
        List<Horraire> horraireList = new ArrayList<>(horraires);
        Collections.sort(horraireList, new Comparator<Horraire>() {
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
        return horraireList;
    }

    public List<Horraire> sortByDemiHeur(List<Horraire> horraires) {
        if (horraires.size()==0)return null;
        Collections.sort(horraires, new Comparator<Horraire>() {
            @Override
            public int compare(Horraire o1, Horraire o2) {
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
        }
        return 0;
    }

}

/*List<Horraire>horraires=new ArrayList<>(sortByJourAndDemiHeur(set));
        List<Object>data=new ArrayList<>();
        Horraire prevHorraire =null;
        Map<String,Object>h=new HashMap<>();

        h.put("jour", horraires.get(0).getJour().getNom());
        h.put("startHour", horraires.get(0).getDemiHeure().getNom());
        h.put("lastHour",null);
        prevHorraire=horraires.get(0);

        for (int i = 1; i < horraires.size(); i++) {
            Horraire horraire=horraires.get(i);
            if (horraire.getJour().equals(prevHorraire.getJour())){
                if (prevHorraire.getDemiHeure().getMin()+30==60 && prevHorraire.getDemiHeure().getHeure()+1==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==0){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else if (prevHorraire.getDemiHeure().getMin()==0 && prevHorraire.getDemiHeure().getHeure()==horraire.getDemiHeure().getHeure() && horraire.getDemiHeure().getMin()==30){
                    h.put("lastHour",horraire.getDemiHeure().getNom());
                }
                else {
                    data.add(h);
                    h=new HashMap<>();
                    h.put("jour", horraire.getJour().getNom());
                    h.put("startHour", horraire.getDemiHeure().getNom());
                }

            }else{
                data.add(h);
                h=new HashMap<>();
                h.put("jour", horraire.getJour().getNom());
                h.put("startHour", horraire.getDemiHeure().getNom());
            }
            if (i==horraires.size()-1)data.add(h);
            prevHorraire =horraire;
        }
        return data;/*
*/