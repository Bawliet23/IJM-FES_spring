package com.stage.project.services;

import com.stage.project.Dao.ActiviteRepository;
import com.stage.project.Dao.EtudiantRepository;
import com.stage.project.Dao.MoisRepository;
import com.stage.project.Dao.PaimentRepository;
import com.stage.project.entities.*;
import com.stage.project.entities.Data.PaimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PaimentServiceImpl implements PaimentService {

    @Autowired
    PaimentRepository paimentRepository;
    @Autowired
    MoisRepository moisRepository;
    @Autowired
    ActiviteRepository activiteRepository;
    @Autowired
    EtudiantRepository etudiantRepository;

    @Override
    public Map<String,Object> getAll(Optional<String>aneee,Optional<String> mois,Optional<Integer> page){
        List<Object> paiments = new ArrayList<>();
        Page<Paiment> pagePaiment;
        if (mois.isPresent() && !mois.get().equals(""))pagePaiment=paimentRepository.findAllByMois(moisRepository.findFirstByNom(mois.get()), PageRequest.of(page.orElse(0), 10,Sort.Direction.DESC, "DatePaiment"));
        else pagePaiment=paimentRepository.findAll(PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "DatePaiment"));
        pagePaiment.forEach(paiment -> paiments.add(paimentData(paiment)));
        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pagePaiment.getTotalPages());
        pageInfos.put("paiments",paiments);
        return pageInfos;
    }

    @Override
    @Transactional
    public Long add(PaimentData paimentData) {
        Activite activite=activiteRepository.findFirstById(paimentData.getActivite_id());
        Etudiant etudiant=etudiantRepository.findFirstById(paimentData.getEtudiant_id());
        if (!activite.getTypeEtudiant().getNom().equals(etudiant.getType().getNom()))return null;
        Paiment paiment=new Paiment(paimentData.getMontantPayee(),paimentData.getMontantRestant(),paimentData.getDatePaiment(),paimentData.getDescription());
        Set<Mois> Listmois=new HashSet<>();
        paimentData.getMois().forEach((mois_id)->{
           Mois mois=moisRepository.findFirstByNom(mois_id.getNom());
           mois.getPaiments().add(paiment);
           paiment.getMois().add(mois);
        });
        paiment.setActivite(activite);
        paiment.setEtudiant(etudiant);
        Paiment p=paimentRepository.save(paiment);
        if (activite instanceof Groupe){
            Groupe groupe= (Groupe) activite;
            if(!groupe.getEtudiants().contains(etudiant)) {
                groupe.getEtudiants().add(etudiant);
                etudiant.getActivitiesGrp().add(groupe);
                activiteRepository.save(groupe);
            }
        }else{
            Individu individu= (Individu) activite;
            if (individu.getEtudiant()==null)
            individu.setEtudiant(etudiant);
            activiteRepository.save(individu);
        }

        return p.getId();
    }

    @Override
    public Long update(PaimentData paimentData) {
        Paiment paiment=paimentRepository.findFirstById(paimentData.getId());
        paiment.setMontantPayee(paimentData.getMontantPayee());
        paiment.setMontantRestant(paimentData.getMontantRestant());
        paiment.setDatePaiment(paimentData.getDatePaiment());
        paiment.setDescription(paimentData.getDescription());
        Set<Mois> listmois=new HashSet<>();
        paimentData.getMois().forEach((mois_id)->{
           Mois mois=moisRepository.findFirstByNom(mois_id.getNom());
           mois.getPaiments().add(paiment);
           listmois.add(mois);
        });
        paiment.setMois(listmois);
        Paiment p=paimentRepository.save(paiment);
        return p.getId();
    }

    @Override
    public Map<String, Object> getOne(Long id) {
        return paimentData(paimentRepository.findFirstById(id));
    }

    @Override
    public Map<String, Object> paimentData(Paiment paiment) {

        Map<String,Object>data=new HashMap<>();
        if (paiment==null)return data;

        data.put("etudiant",paiment.getEtudiant());
        data.put("mois",paiment.getMois());
        data.put("activite",paiment.getActivite());

        data.put("id",paiment.getId());
        data.put("montantPayee",paiment.getMontantPayee());
        data.put("montantRestant",paiment.getMontantRestant());
        data.put("datePaiment",paiment.getDatePaiment());
        data.put("description",paiment.getDescription());

        return data;

    }

    @Override
    public void delete(Long id) {
        paimentRepository.deleteById(id);
    }

}
