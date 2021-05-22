package com.stage.project.services;

import com.stage.project.Dao.*;
import com.stage.project.entities.*;
import com.stage.project.entities.Data.ActiviteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;

@Service
@Transactional
public class ActiviteServiceIml implements ActiviteService {

    @Autowired
    ActiviteRepository activiteRepository;
    @Autowired
    LignNvMatiereRepository nvMatiereRepository;
    @Autowired
    HorraireRepository horraireRepository;
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    SalleRepository salleRepository;
    @Autowired
    GroupeRepository groupeRepository;
    @Autowired
    IndividuRepository individuRepository;


    @Override
    public Map<String,Object> getAll(Optional<String>nom, Optional<Integer> page) {
        List<Object> activites = new ArrayList<>();
        Page<Activite>pageActivite;
        if (nom.isPresent() && !nom.get().equals(""))pageActivite=activiteRepository.findByNomContaining(nom.get(), PageRequest.of(page.orElse(0), 10));
        else pageActivite=activiteRepository.findAll(PageRequest.of(page.orElse(0), 10));
        pageActivite.forEach(activite -> activites.add(activitesData(activite)));

        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pageActivite.getTotalPages());
        pageInfos.put("activites",activites);
        return pageInfos;
    }

    @Override
    public List<Object> lessInofs() {
        List<Object> activites = new ArrayList<>();
        activiteRepository.findAll().forEach(activite -> {
            Map<String,Object>data=new HashMap<>();
            data.put("typeEtudiant", activite.getTypeEtudiant());
            data.put("id", activite.getId());
            data.put("nom", activite.getNom());
            data.put("gratuit",activite.getGratuit());
            data.put("prixEnfant",activite.getLigneNvMatiere().getMatiere().getPrixEnfant());
            data.put("prixAdult",activite.getLigneNvMatiere().getMatiere().getPrixAdult());
            if (activite instanceof Groupe) data.put("type", "groupe");
            else data.put("type", "individu");
            activites.add(data);
        });
        return activites;
    }

    @Override
    public List<Object> getByTypeEtudiantAndType(Long etudiant_id,String typeEtudiant_nom, String type) {
       List<Object> activites = new ArrayList<>();
        //System.out.println("yyyyyyyyyyyyyyyyyy"+etudiant_id+" "+typeEtudiant_nom +" "+type);
       if (type.equals("groupe")){
         //  System.out.println("tttiiioioioio");
            //groupeRepository.findAllByTypeEtudiantNomAndEtudiantsNotContaining(typeEtudiant_nom,etudiantRepository.findFirstById(etudiant_id)).forEach(activite -> {
                groupeRepository.findAllByEtudiantsNotContaining(etudiantRepository.findFirstById(etudiant_id)).forEach(activite -> {
                    Map<String, Object> data = new HashMap<>();
             //   System.out.println("111111222222233333");
                data.put("id", activite.getId());
                data.put("nom", activite.getNom());
                data.put("matiere", activite.getLigneNvMatiere().getMatiere());
                data.put("niveau", activite.getLigneNvMatiere().getNiveau());
                if (activite.getHorraires().size() > 0)
                    data.put("prof", activite.getHorraires().iterator().next().getProf());
                activites.add(data);
            });
        }

       else{
         //  individuRepository.findAllByTypeEtudiantNomAndEtudiantNot(typeEtudiant_nom,etudiantRepository.findFirstById(etudiant_id)).forEach(activite -> {
               individuRepository.findAll().forEach(activite -> {
                   Individu ind=activite;
                   if(ind.getEtudiant()==null)
                   {
                   Map<String, Object> data = new HashMap<>();
                //   System.out.println("111111222222233333");
                data.put("id", activite.getId());
                data.put("nom", activite.getNom());
                data.put("matiere", activite.getLigneNvMatiere().getMatiere());
                data.put("niveau", activite.getLigneNvMatiere().getNiveau());
                if (activite.getHorraires().size() > 0)
                    data.put("prof", activite.getHorraires().iterator().next().getProf());
                activites.add(data);
                   }
            });
       }

        return activites;
    }

    public Long add(ActiviteData activiteData) {
        Activite activite;
        if (activiteData.getType().equals("groupe"))
            activite = new Groupe(activiteData.getNom(), activiteData.getDescription(), activiteData.getGratuit());
        else activite = new Individu(activiteData.getNom(), activiteData.getDescription(), activiteData.getGratuit());
        //System.out.println(activiteData.getNiveau_id());
        LigneNvMatiere ligneNvMatiere = nvMatiereRepository.findFirstByMatiereNomAndNiveauNv(activiteData.getMatiere_id(), activiteData.getNiveau_id());
        activite.setLigneNvMatiere(ligneNvMatiere);
        activite.setTypeEtudiant(activiteData.getTypeEtudiant());
        activite.setSalle(activiteData.getSalle());
        Activite a = activiteRepository.save(activite);
        return a.getId();
    }

    public Long update(ActiviteData activiteData) {
        Activite activite = activiteRepository.findFirstById(activiteData.getId());
        activite.setNom(activiteData.getNom());
        activite.setGratuit(activiteData.getGratuit());
        activite.setDescription(activiteData.getDescription());
        LigneNvMatiere ligneNvMatiere = nvMatiereRepository.findFirstByMatiereNomAndNiveauNv(activiteData.getMatiere_id(), activiteData.getNiveau_id());
        activite.setLigneNvMatiere(ligneNvMatiere);
        if (activiteData.getTypeEtudiant() != null) activite.setTypeEtudiant(activiteData.getTypeEtudiant());
        if (activiteData.getTypeEtudiant()!=null)activite.setSalle(activiteData.getSalle());
        return activiteRepository.save(activite).getId();
    }

    @Override
    public void delete(Long id) {
        activiteRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<String> addEtudiant(Long id, Long etudiantId) {
        Activite activite = activiteRepository.findFirstById(id);
        Etudiant etudiant=etudiantRepository.findFirstById(etudiantId);
        if (activite instanceof Groupe){
            Groupe groupe= (Groupe) activite;
            groupe.getEtudiants().add(etudiant);
            etudiant.getActivitiesGrp().add(groupe);
        }else{
            Individu individu= (Individu) activite;
            individu.setEtudiant(etudiant);
            etudiant.getActivitiesInd().add(individu);
        }
        activiteRepository.save(activite);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void removeEtudiant(Long id, Long etudiantId) {
        Activite activite = activiteRepository.findFirstById(id);
        Etudiant etudiant = etudiantRepository.findFirstById(etudiantId);
        if (activite instanceof Groupe) {
            Groupe groupe = (Groupe) activite;
            groupe.getEtudiants().remove(etudiant);
            etudiant.getActivitiesGrp().remove(groupe);
            activiteRepository.save(groupe);
            return;
        }

        Individu individu = (Individu) activite;
        individu.setEtudiant(null);
        activiteRepository.save(individu);
    }

    @Override
    public Collection<Etudiant> getEtudiants(Long id) {
        Activite activite = activiteRepository.findFirstById(id);
        if (activite instanceof Groupe) {
            Groupe groupe = (Groupe) activite;
            return groupe.getEtudiants();
        }
        Individu individu = (Individu) activite;
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(individu.getEtudiant());
        if (etudiants.size() > 0) return etudiants;
        return null;
    }

    @Override
    public Map<String, Object> getOne(Long id) {
        return activitesData(activiteRepository.findFirstById(id));
    }

    @Override
    public Map<String, Object> activitesData(Activite activite) {
        Map<String, Object> data = new HashMap<>();
        if (activite == null) return data;

        data.put("matiere", activite.getLigneNvMatiere().getMatiere());
        data.put("niveau", activite.getLigneNvMatiere().getNiveau());
        data.put("horaires", horairesData(sortByJourAndDemiHeur(activite.getHorraires())));
        if (activite.getHorraires().size() > 0) data.put("prof", activite.getHorraires().iterator().next().getProf());
        data.put("salle", activite.getSalle());
        data.put("typeEtudiant", activite.getTypeEtudiant());
        data.put("id", activite.getId());
        data.put("nom", activite.getNom());
        data.put("description", activite.getDescription());
        data.put("gratuit", activite.getGratuit());
        if (activite instanceof Groupe) data.put("type", "groupe");
        else data.put("type", "individu");
        return data;
    }

    @Override
    public void addHoraire(Long id, Horraire horraire) {
        Activite activite=activiteRepository.findFirstById(id);
        Horraire horraire1=new Horraire();
        horraire1.setId(activite.getId()+""+horraire.getJour().getNom()+""+horraire.getDemiHeure().getNom());
        horraire1.setJour(horraire.getJour());
        horraire1.setDemiHeure(horraire.getDemiHeure());
        horraire1.setActivite(activite);
        horraireRepository.save(horraire1);
    }

    @Override
    public void deleteHoraire(Long id, String horaire_id) {
        String jour_id=horaire_id.split(",")[0];
        String demiHeure=horaire_id.split(",")[1];
       // System.out.println(jour_id+"*"+demiHeure+"*"+id);
        Horraire horraire=horraireRepository.findFirstByDemiHeureNomAndJourNomAndActiviteId(demiHeure,jour_id,id);
     //   System.out.println(horraire.getDemiHeure().getNom());
        if (horraire.getProf()==null)horraireRepository.delete(horraire);
        else {
            horraire.setActivite(null);
            horraireRepository.save(horraire);
        }

    }

    @Override
    public void addProf(Long id, String horaire_id, String cin) {
        Activite activite=activiteRepository.findFirstById(id);
        String jour_id=horaire_id.split(",")[0];
        String demiHeure=horaire_id.split(",")[1];
        Horraire horraire=horraireRepository.findFirstByDemiHeureNomAndJourNomAndProfCin(demiHeure,jour_id,cin);
        horraire.setActivite(activite);
        horraireRepository.save(horraire);
        Horraire horraire1=horraireRepository.findFirstById(id+jour_id+demiHeure);
        if (horraire1!=null)horraireRepository.delete(horraire1);
        activiteRepository.save(activite);
    }

    @Override
    public ArrayList<Object> getHoraires(Long id) {
        ArrayList<Object>data=new ArrayList<>();
        List<String>allHoraires=new ArrayList<>();
        Activite activite=activiteRepository.findFirstById(id);
        activite.getLigneNvMatiere().getProfs().forEach(prof ->  {
            prof.getHorraires().forEach(horraire -> {
                HashMap<String,Object>h=new HashMap<>();
                if (horraire.getActivite()==activite){
                    if (allHoraires.contains(horraire.getDemiHeure().getNom()+"/"+horraire.getJour().getNom())){
                        HashMap<String,Object>d= (HashMap<String, Object>) data.get(allHoraires.indexOf(horraire.getDemiHeure().getNom()+"/"+horraire.getJour().getNom()));
                        boolean val= (Boolean) d.get("attribue");
                        if (!val){
                            d.put("attribue",true);
                            d.put("prof",prof.getPrenom()+" "+prof.getNom());
                        }
                    }else{
                        h.put("demiHeure",horraire.getDemiHeure().getNom());
                        h.put("jour",horraire.getJour().getNom());
                        h.put("attribue",true);
                        h.put("prof",prof.getPrenom()+" "+prof.getNom());
                        data.add(h);
                        allHoraires.add(horraire.getDemiHeure().getNom()+"/"+horraire.getJour().getNom());
                    }
                }
                else if (horraire.getActivite()==null  && !allHoraires.contains(horraire.getDemiHeure().getNom()+"/"+horraire.getJour().getNom())){
                    h.put("demiHeure",horraire.getDemiHeure().getNom());
                    h.put("jour",horraire.getJour().getNom());
                    h.put("attribue",false);
                    data.add(h);
                    allHoraires.add(horraire.getDemiHeure().getNom()+"/"+horraire.getJour().getNom());
                }
            });
        });
        return data;
    }

    @Override
    public List<Map> getSeances(Long id) {
        List<Map>list=new ArrayList<>();
        Activite activite=activiteRepository.findFirstById(id);
        activite.getSeances().forEach(seance -> {
            Map<String,Object>data=new HashMap<>();
            data.put("id",seance.getId());
            data.put("date",seance.getDate());
            data.put("description",seance.getDescription());
            list.add(data);
        });
        return list;
    }

    public List<Object>horairesData(List<Horraire>horraires){
        if (horraires==null)return null;
        List<Object>data=new ArrayList<>();
        Horraire prevHorraire =null;
        Map<String,Object>h=new HashMap<>();

        h.put("jour", horraires.get(0).getJour().getNom());
        h.put("startHour", horraires.get(0).getDemiHeure().getNom());
        if(horraires.size()==1)
        {
            h.put("lastHour",horraires.get(0).getDemiHeure().getNom());
            data.add(h);
            return data;
        }
        h.put("lastHour",null);
        prevHorraire=horraires.get(0);

        for (int i = 1; i < horraires.size(); i++) {
            Horraire horraire=horraires.get(i);
          //  System.out.println(horraire.getJour().getNom());
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
        return data;
    }
private String add30Min(String value){
 if(value.contains("30"))
     return Integer.valueOf(value.substring(0,value.indexOf(":")))+1+"00";
 else
     return Integer.valueOf(value.substring(0,value.indexOf(":")))+"30";
}
    public List<Horraire> sortByJourAndDemiHeur(Set<Horraire> horraires) {
        if (horraires.size()==0)return null;
        if(horraires.size()==1) return  new ArrayList<>(horraires);
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
