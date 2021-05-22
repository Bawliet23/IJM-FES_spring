package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.project.Dao.*;
import com.stage.project.config.RandomString;
import com.stage.project.entities.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;


@Service
@Transactional
public class EtudiantServiceIml implements EtudiantService {


    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    ActiviteRepository activiteRepository;
    @Autowired
    GroupeRepository groupeRepository;
    @Autowired
    PaimentRepository paimentRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    IndividuRepository individuRepository;
    @Autowired
    ServletContext context;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String,Object> getAll(Optional<String>query,Optional<Integer>page) {
        List<Object> etudiants = new ArrayList<>();
        Page<Etudiant>pageEtudiant;

        if (query.isPresent() && !query.get().equals("")){
            if(!isNumeric(query.get()))
            pageEtudiant=etudiantRepository.findByPrenomContainingOrNomContaining(query.orElse("_"),query.orElse("_"), PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, "numero"));
            else
                pageEtudiant=etudiantRepository.findByNumeroEquals(Integer.valueOf(query.get()), PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, "numero"));
        }
        else pageEtudiant=etudiantRepository.findAll(PageRequest.of(page.orElse(0), 10, Sort.Direction.ASC, "numero"));
        pageEtudiant.forEach(etudiant -> etudiants.add(etudiantData(etudiant)));

        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pageEtudiant.getTotalPages());
        pageInfos.put("etudiants",etudiants);
        return pageInfos;
    }

    @Override
    public List<Etudiant> findByType(String type_nom) {
        return etudiantRepository.findAllByType_Nom(type_nom);
    }

    @Override
    public Map<String,Object> findByNotEqualActivite(Optional<String> query, Optional<Integer>page,Long id) {
        List<Object> etudiants = new ArrayList<>();
        Page<Etudiant>pageEtudiant;
        Activite activite=activiteRepository.findFirstById(id);
        if (activite instanceof Groupe){
            Groupe groupe= (Groupe) activite;
            if (query.isPresent() && !query.get().equals(""))pageEtudiant= etudiantRepository.findAllByActivitiesGrpIsNotContainingAndPrenomContainingOrNomContainingAndTypeNom(groupe,query.get(),query.get(),activite.getTypeEtudiant().getNom(),PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "dateInscription"));
            else pageEtudiant= etudiantRepository.findAllByActivitiesGrpIsNotContainingAndTypeNom(groupe,activite.getTypeEtudiant().getNom(),PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "dateInscription"));
        }else{
            Individu individu= (Individu) activite;
            if (query.isPresent() && !query.get().equals(""))pageEtudiant= etudiantRepository.findAllByActivitiesIndIsNotContainingAndPrenomContainingOrNomContainingAndTypeNom(individu,query.get(),query.get(),activite.getTypeEtudiant().getNom(),PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "dateInscription"));
            else pageEtudiant= etudiantRepository.findAllByActivitiesIndIsNotContainingAndTypeNom(individu,activite.getTypeEtudiant().getNom(),PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "dateInscription"));
        }
        pageEtudiant.forEach(etudiant -> etudiants.add(etudiantData(etudiant)));
        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pageEtudiant.getTotalPages());
        pageInfos.put("etudiants",etudiants);
        return pageInfos;
    }

    @Override
    public ResponseEntity<String> add(MultipartFile file, String etudiantString) throws JsonProcessingException {
        Etudiant etudiant = new ObjectMapper().readValue(etudiantString, Etudiant.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("/Images/")).mkdir();
        }
        if(file!=null) {
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename) + Calendar.getInstance().getTimeInMillis() + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
            try {
                FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }

            etudiant.setPhoto(newFileName);
        }
        etudiantRepository.save(etudiant);
        Role role=roleRepository.findFirstByRoleName("ROLE_ETUDIANT");
        String passKey= RandomString.getAlphaNumericString(6);
        String password=passwordEncoder.encode(passKey);
        User user=new User(etudiant.getPrenom(),etudiant.getNom(),etudiant.getNumero()+"",password,passKey,"null");
        user.setRole(role);
        user.setEtudiant(etudiant);
        System.out.println("user ="+ user);
        userRepository.save(user);
        ResponseEntity<String> responseEntity= new ResponseEntity<>(passKey,HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public Etudiant update(MultipartFile file, String etudiantString) throws JsonProcessingException {
        Etudiant etudiant = new ObjectMapper().readValue(etudiantString, Etudiant.class);
        Etudiant et=etudiantRepository.findFirstById(etudiant.getId());
        if(file!=null){
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
            et.setPhoto(newFileName);
        }
        et.setNumero(etudiant.getNumero());
        et.setNom(etudiant.getNom());
        et.setPrenom(etudiant.getPrenom());
        et.setNomArabe(etudiant.getNomArabe());
        et.setPrenomArabe(etudiant.getPrenomArabe());
        et.setDateNaissance(etudiant.getDateNaissance());
        et.setType(etudiant.getType());
        et.setMetier(etudiant.getMetier());
        et.setRemarque(etudiant.getRemarque());
        et.setAdresse(etudiant.getAdresse());
        et.setDateInscription(etudiant.getDateInscription());
        et.setTele1(etudiant.getTele1());
        et.setTele2(etudiant.getTele2());
        et.setAge(etudiant.getAge());
        return etudiantRepository.save(et);
    }

    @Override
    public void delete(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getOne(Long id) {
        return etudiantData(etudiantRepository.findFirstById(id));
    }


    @Override
    public byte[] getImage(Long id) {
        Etudiant etudiant   = etudiantRepository.findFirstById(id);
        try {
            return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+etudiant.getPhoto()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> addActivite(Long id, Long activite_id) {
        ResponseEntity<String>responseEntity=new ResponseEntity<>(HttpStatus.OK);
        if (paimentRepository.findAllByActiviteIdAndEtudiantId(activite_id,id).size()==0) responseEntity= new ResponseEntity<>("ce etudiant n'a pas payé pour cette activité",HttpStatus.OK);
        Activite activite=activiteRepository.findFirstById(activite_id);
        Etudiant etudiant=etudiantRepository.findFirstById(id);
        if (activite.getType().equals("groupe")){
            Groupe groupe= (Groupe) activite;
            if (groupe.getEtudiants().contains(etudiant))return new ResponseEntity<>("activite already has etudiant",HttpStatus.CONFLICT);
            groupe.getEtudiants().add(etudiant);
            etudiant.getActivitiesGrp().add(groupe);
        }else{
            Individu individu= (Individu) activite;
            if (individu.getEtudiant()!=null)return new ResponseEntity<>("L'activité est pleine",HttpStatus.CONFLICT);
            else individu.setEtudiant(etudiant);
        }
        activiteRepository.save(activite);
        return responseEntity;
    }

    @Override
    public ResponseEntity<String> deleteActivite(Long id, Long activite_id) {
        Activite activite=activiteRepository.findFirstById(activite_id);
        Etudiant etudiant=etudiantRepository.findFirstById(id);
        if (activite.getType().equals("groupe")){
            Groupe groupe= (Groupe) activite;
            groupe.getEtudiants().remove(etudiant);
            etudiant.getActivitiesGrp().remove(groupe);
        }else{
            Individu individu= (Individu) activite;
            individu.setEtudiant(null);
        }
        activiteRepository.save(activite);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<Map> getEtudiantsByExam(Long id) {
        List<Etudiant>etudiants=new ArrayList<>();
        Exam exam=examRepository.findFirstById(id);
        exam.getLigneNvMatiere().getActivites().forEach((activite -> {
            System.out.println(activite.getLigneNvMatiere().getMatiere().getNom());
            if (activite instanceof Groupe){
                Groupe groupe= (Groupe) activite;
                etudiants.addAll(groupe.getEtudiants());
            }else{
                Individu individu= (Individu) activite;
                etudiants.add(individu.getEtudiant());
            }
        }));
        List<Map>data=new ArrayList<>();
        etudiants.forEach((etudiant -> {
            HashMap<String,Object>et=new HashMap<>();
            et.put("etudiant",etudiant);
            et.put("etudiant_id",etudiant.getId());
            Note note=noteRepository.findFirstByExamIdAndAndEtudiantId(id,etudiant.getId());
            if (note!=null) {
                et.put("note_id", note.getId());
                et.put("note", note.getNote());
                et.put("remarque", note.getRemarque());
                et.put("decision", note.getDecision());
            }
            data.add(et);
        }));
        return data;
    }

    public Map<String, Object> etudiantData(Etudiant etudiant) {

        Map<String, Object> data = new HashMap<>();
        if (etudiant == null) return data;

        data.put("type", etudiant.getType());
        data.put("notes", etudiant.getNotes());
        data.put("paiments", etudiant.getPaiments());
        data.put("preseneces", etudiant.getPresences());

        List<Map> activitesGrp = new ArrayList<>();
        etudiant.getActivitiesGrp().forEach((activite) -> activitesGrp.add(activitesData(activite)));
        data.put("activitiesGrp", activitesGrp);

        List<Map> activitesIndv = new ArrayList<>();
        etudiant.getActivitiesInd().forEach((activite) -> activitesIndv.add(activitesData(activite)));
        data.put("activitiesIndv", activitesIndv);

        data.put("id", etudiant.getId());
        data.put("numero", etudiant.getNumero());
        data.put("nom", etudiant.getNom());
        data.put("prenom", etudiant.getPrenom());
        data.put("nomArabe", etudiant.getNomArabe());
        data.put("prenomArabe", etudiant.getPrenomArabe());
        data.put("age", etudiant.getAge());
        data.put("dateNaissance", etudiant.getDateNaissance());
        data.put("adresse", etudiant.getAdresse());
        data.put("tele1", etudiant.getTele1());
        data.put("remarque",etudiant.getRemarque());
        data.put("metier",etudiant.getMetier());
        data.put("tele2", etudiant.getTele2());
        data.put("photo", etudiant.getPhoto());
        data.put("dateInscription", etudiant.getDateInscription());

        return data;

    }

    public Map<String, Object> activitesData(Activite activite) {

        Map<String, Object> data = new HashMap<>();
        if (activite == null) return data;

        data.put("matiere", activite.getLigneNvMatiere().getMatiere());
        data.put("niveau", activite.getLigneNvMatiere().getNiveau());
        data.put("horraires", activite.getHorraires());
        if (activite.getHorraires().size()>0)data.put("prof", activite.getHorraires().iterator().next().getProf());

        data.put("id", activite.getId());
        data.put("nom", activite.getNom());
        data.put("description", activite.getDescription());
        data.put("gratuit", activite.getGratuit());

        return data;

    }

}
