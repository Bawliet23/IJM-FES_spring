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
@Table(name = "examens")
@EntityListeners(AuditingEntityListener.class)
public class Exam extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="annee_id",nullable = false)
    private Annee annee;

    @ManyToOne()
    @JoinColumn(name="ligneNvMatiere_id",nullable = false)
    private LigneNvMatiere ligneNvMatiere;

    @OneToMany(mappedBy = "exam",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Note> notes=new HashSet<>();

    @NotNull
    private String titre;

    private String file;

    public Exam() {
    }

    public Exam(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public LigneNvMatiere getLigneNvMatiere() {
        return ligneNvMatiere;
    }

    public void setLigneNvMatiere(LigneNvMatiere ligneNvMatiere) {
        this.ligneNvMatiere = ligneNvMatiere;
    }

    @JsonIgnore
    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                '}';
    }
}
