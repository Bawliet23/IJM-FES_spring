package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.entities.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public interface EtudiantService {
    Map<String,Object> getAll(Optional<String> query, Optional<Integer>page);
    List<Etudiant> findByType(String type_nom);
    Map<String,Object> findByNotEqualActivite(Optional<String> query, Optional<Integer>page,Long id);
    ResponseEntity<String> add(MultipartFile file,String etudiant) throws JsonProcessingException;
    Etudiant update(MultipartFile file,String etudiant) throws JsonProcessingException;
    void delete(Long id);
    Map<String,Object> getOne(Long id);
    Map<String,Object> etudiantData(Etudiant etudiant);
    byte[] getImage(Long id);
    ResponseEntity<String> addActivite(Long id,Long activite_id);
    ResponseEntity<String> deleteActivite(Long id, Long activite_id);
    List<Map> getEtudiantsByExam(Long id);
    void sendNotification(List<Etudiant> etudiants);
}
