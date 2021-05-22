package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.project.Dao.LignNvMatiereRepository;
import com.stage.project.Dao.MatiereRepository;
import com.stage.project.Dao.NiveauRepository;
import com.stage.project.entities.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {

    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    LignNvMatiereRepository lignNvMatiereRepository;
    @Autowired
    NiveauRepository niveauRepository;
    @Autowired
    ServletContext context;

    @Override
    public Page<Matiere> getAll(Optional<String>nom, Optional<Integer> page) {
        if (nom.isPresent() && !nom.get().equals(""))return matiereRepository.findByNomContaining(nom.get(), PageRequest.of(page.orElse(0), 10));
        else return matiereRepository.findAll(PageRequest.of(page.orElse(0), 10));
    }

    public List<Matiere>getAllPages(){
        return matiereRepository.findAll();
    }

    @Override
    public Map<String, Object> getOne(String nom) {
        Matiere matiere=matiereRepository.findFirstByNom(nom);
        Map<String, Object> data = new HashMap<>();
        if (matiere == null) return data;
        data.put("nom",matiere.getNom());
        data.put("description",matiere.getDescription());
        data.put("photo",matiere.getPhoto());
      //  ArrayList<Integer> nvs=new ArrayList<Integer>();
      //  for (LigneNvMatiere l:matiere.getLigneNvsMatieres()) {
      //      nvs.add(l.getNiveau().getNv());
      //  }
      //  data.put("niveaux",nvs);
        data.put("prixEnfant",matiere.getPrixEnfant());
        data.put("prixAdult",matiere.getPrixAdult());
        Set<Object> activites=new HashSet<>();
        matiere.getLigneNvsMatieres().forEach(ligneNvMatiere -> activites.addAll(ligneNvMatiere.getActivites()));
        data.put("activites",activites);
        Set<Prof>profs=new HashSet<>();
        matiere.getLigneNvsMatieres().forEach(ligneNvMatiere -> profs.addAll(ligneNvMatiere.getProfs()));
        data.put("profs",profs);
        List<Niveau>niveaux=new ArrayList<>();
        matiere.getLigneNvsMatieres().forEach(ligneNvMatiere -> niveaux.add(ligneNvMatiere.getNiveau()));
        Collections.sort(niveaux, new Comparator<Niveau>() {
            @Override
            public int compare(Niveau o1, Niveau o2) {
                if (o1.getNv()>o2.getNv())return 1;
                else if (o1.getNv()<o2.getNv())return -1;
                return 0;
            }
        });
        data.put("niveaux",niveaux);
        return data;
    }

    @Override
    public byte[] getImage(String nom) {
        Matiere matiere   = matiereRepository.findFirstByNom(nom);
        try {
            return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+matiere.getPhoto()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Matiere add(MultipartFile file, String matiere_String, String niveaux_string) throws JsonProcessingException {
        Matiere matiere = new ObjectMapper().readValue(matiere_String, Matiere.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("/Images/")).mkdir();
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) +Calendar.getInstance().getTimeInMillis()+"." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        matiere.setPhoto(newFileName);
        matiereRepository.save(matiere);
        List<String> niveaux = new ArrayList<String>(Arrays.asList(niveaux_string.split(",")));
        niveaux.forEach((nv -> {
            Niveau niveau=niveauRepository.findFirstByNv(Integer.parseInt(nv));
            LigneNvMatiere ligneNvMatiere=new LigneNvMatiere();
            ligneNvMatiere.setId(matiere.getNom()+nv);
            ligneNvMatiere.setNiveau(niveau);
            ligneNvMatiere.setMatiere(matiere);
            lignNvMatiereRepository.save(ligneNvMatiere);
        }));
        return matiere;
    }

    @Override
    @Transactional
    public Matiere update(String nom,MultipartFile file, String matiere_string, String niveaux_string) throws JsonProcessingException {
        Matiere matiere=matiereRepository.findFirstByNom(nom);
        Matiere newmatiere = new ObjectMapper().readValue(matiere_string, Matiere.class);
        if (file!=null) {
            boolean isExit = new File(context.getRealPath("/Images/")).exists();
            if (!isExit) {
                new File(context.getRealPath("/Images/")).mkdir();
            }
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename) + Calendar.getInstance().getTimeInMillis() + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
            try {
                FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
            matiere.setPhoto(newFileName);
        }

        matiere.setDescription(newmatiere.getDescription());
        if (!newmatiere.getNom().equals(matiere.getNom()))matiere.setNom(newmatiere.getNom());
        matiere.setPrixAdult(newmatiere.getPrixAdult());
        matiere.setPrixEnfant(newmatiere.getPrixEnfant());

        ArrayList<String> niveaux = new ArrayList<>(Arrays.asList(niveaux_string.split(",")));
        if (niveaux.size()>0 && !niveaux_string.isEmpty()) {
            lignNvMatiereRepository.deleteAllByMatiereNom(matiere.getNom());
            niveaux.forEach((nv -> {
                if (lignNvMatiereRepository.findFirstByMatiereNomAndNiveauNv(matiere.getNom(), Integer.parseInt(nv)) == null) {
                    Niveau niveau = niveauRepository.findFirstByNv(Integer.parseInt(nv));
                    LigneNvMatiere ligneNvMatiere = new LigneNvMatiere();
                    ligneNvMatiere.setId(matiere.getNom() + nv);
                    ligneNvMatiere.setNiveau(niveau);
                    ligneNvMatiere.setMatiere(matiere);
                    lignNvMatiereRepository.save(ligneNvMatiere);
                }
            }));
        }
        return matiereRepository.save(matiere);
    }


    @Override
    public Matiere addNiveaux(String nom, List<Niveau> niveaux) {
        Matiere matiere=matiereRepository.findFirstByNom(nom);
        niveaux.forEach((niveau -> {
            LigneNvMatiere ligneNvMatiere=new LigneNvMatiere();
            ligneNvMatiere.setId(nom+niveau.getNv());
            ligneNvMatiere.setNiveau(niveau);
            ligneNvMatiere.setMatiere(matiere);
            lignNvMatiereRepository.save(ligneNvMatiere);
        }));
        return matiere;
    }

    @Override
    public boolean delete(String nom) {
        Matiere matiere=matiereRepository.findFirstByNom(nom);
        Boolean[] d = {true};
        matiere.getLigneNvsMatieres().forEach((ligneNvMatiere -> {
            if (!ligneNvMatiere.getActivites().isEmpty())  d[0]=false;;
        }));
        if(d[0]) {
            matiereRepository.delete(matiere);
            return true;
        }
        return false;
    }
}
