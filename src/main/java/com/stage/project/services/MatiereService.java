package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.entities.Matiere;
import com.stage.project.entities.Niveau;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MatiereService {
    Page<Matiere> getAll(Optional<String> nom, Optional<Integer> page);
    List<Matiere>getAllPages();
    Map<String,Object> getOne(String nom);
    byte[] getImage(String nom);
    Matiere add(MultipartFile file,String matiere,String niveaux) throws JsonProcessingException;
    Matiere update(String nom,MultipartFile file,String matiere,String niveaux)throws JsonProcessingException;
    Matiere addNiveaux(String nom, List<Niveau> niveaux);
    boolean delete(String nom);
}
