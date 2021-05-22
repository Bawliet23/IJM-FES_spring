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
@Table(name = "presences")
@EntityListeners(AuditingEntityListener.class)
public class Presence extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="etudiant_id",nullable = false)
    private Etudiant etudiant;

    @ManyToOne()
    @JoinColumn(name="seance_id",nullable = false)
    private Seance seance;

    @NotNull
    private boolean absent;
    private String remarque;

    public Presence() {
    }

    public Presence(@NotNull boolean absent, String remarque) {
        this.absent = absent;
        this.remarque = remarque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }
}
