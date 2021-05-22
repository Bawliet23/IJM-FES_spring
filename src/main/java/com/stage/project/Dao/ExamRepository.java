package com.stage.project.Dao;

import com.stage.project.entities.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Long> {
    Exam findFirstById(Long id);
    Page<Exam> findByAnneeNom(String annee, Pageable pageable);
    Page<Exam> findAll(Pageable pageable);
}
