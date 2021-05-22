package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="etudiant_id",nullable = false)
    private Etudiant etudiant;

    @ManyToOne()
    @JoinColumn(name="exam_id",nullable = false)
    private Exam exam;

    @NotNull
    private String note;
    private String remarque;
    private String decision;

    public Note() {
    }

    public Note(@NotNull String note, String remarque, String decision) {
        this.note = note;
        this.remarque = remarque;
        this.decision = decision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @JsonIgnore
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", note='" + note + '\'' +
                ", remarque='" + remarque + '\'' +
                ", decision='" + decision + '\'' +
                '}';
    }
}
