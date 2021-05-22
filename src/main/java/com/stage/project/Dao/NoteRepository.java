package com.stage.project.Dao;

import com.stage.project.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
    Note findFirstById(Long id);
    Note findFirstByExamIdAndAndEtudiantId(Long exam_id,Long etudiant_id);
}
