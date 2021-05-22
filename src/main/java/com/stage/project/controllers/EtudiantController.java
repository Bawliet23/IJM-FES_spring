package com.stage.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.Dao.EtudiantRepository;
import com.stage.project.entities.*;
import com.stage.project.entities.Data.NoteData;
import com.stage.project.services.EtudiantService;
import com.stage.project.services.EtudiantServiceIml;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    EtudiantService etudiantService;

    @GetMapping
    public Map<String,Object> index(@RequestParam Optional<String>query, @RequestParam Optional<Integer>page) {
        return etudiantService.getAll(query,page);
    }

    @GetMapping("type/{type}")
    public List<Etudiant> findByType(@PathVariable("type") String type){
        return etudiantService.findByType(type);
    }

    @GetMapping("activite")
    public Map<String,Object> findByNoInActivite(@RequestParam Optional<String>query, @RequestParam Optional<Integer>page, @RequestParam Long id){
        return etudiantService.findByNotEqualActivite(query, page, id);
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestParam(name="file",required = false) MultipartFile file,
			 @RequestParam("etudiant") String etudiant) throws JsonProcessingException {
        return etudiantService.add(file,etudiant);
    }

    @PatchMapping("{id}")
    public Etudiant update(@PathVariable("id") Long id,@RequestParam(value = "file", required = false) MultipartFile file ,
			 @RequestParam("etudiant") String etudiant) throws JsonProcessingException {
        return etudiantService.update(file,etudiant);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        etudiantService.delete(id);
    }

    @GetMapping("{id}")
    public Map<String,Object> get(@PathVariable("id") Long id) {
        return etudiantService.getOne(id);
    }

    @GetMapping(path="/photo/{id}")
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        return etudiantService.getImage(id);
    }

    @PostMapping("{id}/activites")
    public ResponseEntity<String> addActivite(@PathVariable("id") Long id, @RequestParam("activite_id") Long activite_id){
        return etudiantService.addActivite(id,activite_id);
    }

    @DeleteMapping("{id}/activites/{activite_id}")
    public ResponseEntity<String> deleteActivite(@PathVariable("id") Long id,@PathVariable("activite_id") Long activite_id){
        return etudiantService.deleteActivite(id,activite_id);
    }

    @GetMapping("exam/{id}")
    public List<Map>getEtudiantsByExam(@PathVariable("id") Long id){
        return etudiantService.getEtudiantsByExam(id);
    }

}
