package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stage.project.entities.Data.NoteData;
import com.stage.project.entities.Exam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExamService {
    Map<String, Object> getAll(Optional<String> annne, Optional<Integer> page);
    Long add(MultipartFile file, String exam) throws JsonProcessingException;
    void delete(Long id);
    Map<String,Object> getOne(Long id);
    Map<String,Object> examData(Exam exam);
    boolean addNotes(Long id, List<NoteData>notesData);
    ResponseEntity<byte[]> getFile(Long id) throws IOException;
    Long update(MultipartFile file, String exam) throws JsonProcessingException;
}
