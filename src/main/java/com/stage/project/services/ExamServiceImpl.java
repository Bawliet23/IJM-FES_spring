package com.stage.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.project.Dao.EtudiantRepository;
import com.stage.project.Dao.ExamRepository;
import com.stage.project.Dao.NoteRepository;
import com.stage.project.entities.Data.NoteData;
import com.stage.project.entities.Etudiant;
import com.stage.project.entities.Exam;
import com.stage.project.entities.Note;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepository examRepository;
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    ServletContext context;

    @Override
    public Map<String, Object> getAll(Optional<String> annee, Optional<Integer> page) {
        List<Object> examens = new ArrayList<>();
        Page<Exam> pageExam;
        if (annee.isPresent() && !annee.get().equals(""))pageExam=examRepository.findByAnneeNom(annee.get(), PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "annee"));
        else pageExam=examRepository.findAll(PageRequest.of(page.orElse(0), 10, Sort.Direction.DESC, "annee"));
        pageExam.forEach(exam -> examens.add(examData(exam)));

        Map<String,Object>pageInfos=new HashMap<>();
        pageInfos.put("TotalPages",pageExam.getTotalPages());
        pageInfos.put("examens",examens);
        return pageInfos;
    }

    @Override
    public void delete(Long id) {
        examRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getOne(Long id) {
        return examData(examRepository.findFirstById(id));
    }

    @Override
    public Map<String, Object> examData(Exam exam) {
        Map<String,Object>data=new HashMap<>();
        if (exam==null)return data;
        data.put("id",exam.getId());
        data.put("titre",exam.getTitre());
        data.put("annee",exam.getAnnee());
        data.put("matiere",exam.getLigneNvMatiere().getMatiere());
        data.put("niveau",exam.getLigneNvMatiere().getNiveau());
        List<Object>notes=new ArrayList<>();
        exam.getNotes().forEach((note -> notes.add(noteData(note))));
        data.put("notes",notes);
        return data;
    }

    @Override
    public boolean addNotes(Long id, List<NoteData> notesData) {
        Exam exam=examRepository.findFirstById(id);
        boolean []d={false};
        notesData.forEach(noteData -> {
            if (noteRepository.findFirstById(noteData.getNote_id())!=null){
                Note note=noteRepository.findFirstById(noteData.getNote_id());
                note.setDecision(noteData.getDecision());
                note.setNote(noteData.getNote());
                note.setRemarque(noteData.getRemarque());
                noteRepository.save(note);
                d[0] = true;
            }else {
                Note note = new Note(noteData.getNote(), noteData.getRemarque(), noteData.getDecision());
                Etudiant etudiant = etudiantRepository.findFirstById((long) noteData.getEtudiant_id());
                note.setEtudiant(etudiant);
                note.setExam(exam);
                noteRepository.save(note);
                d[0] = true;
            }
        });
        return d[0];
    }

    @Override
    public ResponseEntity<byte[]> getFile(Long id) throws IOException {
        Exam exam=examRepository.findFirstById(id);
        if (exam.getFile()==null)return null;
        byte[] contents=null;
        try {
            contents= Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+exam.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

         HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = exam.getFile();
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    @Override
    public Long update(MultipartFile file, String exam_string) throws JsonProcessingException {
        Exam exam = new ObjectMapper().readValue(exam_string, Exam.class);
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
            exam.setFile(newFileName);
        }
        return (examRepository.save(exam)).getId();

    }

    @Override
    public Long add(MultipartFile file, String exam_string) throws JsonProcessingException {
        Exam exam = new ObjectMapper().readValue(exam_string, Exam.class);
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
        exam.setFile(newFileName);
        return (examRepository.save(exam)).getId();
    }

    private Map<String, Object>noteData(Note note){
        Map<String,Object>data=new HashMap<>();
        if (note==null)return data;
        data.put("id",note.getId());
        data.put("note",note.getNote());
        data.put("remarque",note.getRemarque());
        data.put("decision",note.getDecision());
        data.put("etudiant_name",note.getEtudiant().getPrenom()+" "+note.getEtudiant().getNom());
        data.put("etudiant_id",note.getEtudiant().getId());
        return data;
    }
}
