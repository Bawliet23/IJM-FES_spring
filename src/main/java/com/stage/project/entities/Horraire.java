package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "horraires")
@EntityListeners(AuditingEntityListener.class)
public class Horraire extends Auditable<String>{

    @Id
    @Column(name = "id", nullable = false,unique = true)
    private String id;

    @ManyToOne()
    @JoinColumn(name="demiHeure_id",nullable = false)
    private DemiHeure demiHeure;

    @ManyToOne()
    @JoinColumn(name="jour_id",nullable = false)
    private Jour jour;

    @ManyToOne()
    @JoinColumn(name="prof_id")
    private Prof prof;

    @ManyToOne()
    @JoinColumn(name="activite_id")
    private Activite activite;

    public Horraire() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public DemiHeure getDemiHeure() {
        return demiHeure;
    }

    public void setDemiHeure(DemiHeure demiHeure) {
        this.demiHeure = demiHeure;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    @JsonIgnore
    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    @JsonIgnore
    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "Horraire{" +
                "id='" + id + '\'' +
                '}';
    }
}
