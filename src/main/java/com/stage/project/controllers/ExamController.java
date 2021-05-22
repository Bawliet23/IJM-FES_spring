package com.stage.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.entities.Data.NoteData;
import com.stage.project.entities.Exam;
import com.stage.project.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/examens")
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping
    public Map<String,Object> index(@RequestParam Optional<String> annee, @RequestParam Optional<Integer>page){
        return examService.getAll(annee,page);
    }

    @PostMapping
    public Long store(@RequestParam("file") MultipartFile file,
			 @RequestParam("exam") String exam) throws JsonProcessingException {
        return examService.add(file,exam);
    }

    @PutMapping("{id}")
    public Long update(@PathVariable("id") Long id,@RequestParam(value = "file", required = false) MultipartFile file ,
			@RequestParam("exam") String exam) throws JsonProcessingException {
        return examService.update(file,exam);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        examService.delete(id);
    }

    @GetMapping("{id}")
    public Map<String,Object> get(@PathVariable("id") Long id) {
        return examService.getOne(id);
    }

    @PostMapping("{id}/addNotes")
    public boolean addNotes(@PathVariable("id") Long id, @RequestBody List<NoteData> notesData){
        return examService.addNotes(id,notesData);
    }

    @GetMapping("{id}/file")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id) throws IOException {
        return examService.getFile(id);
    }
}
