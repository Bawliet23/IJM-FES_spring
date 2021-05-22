package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "annees")
@EntityListeners(AuditingEntityListener.class)

public class Annee extends Auditable<String> {

    @OneToMany(mappedBy = "annee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Mois> mois=new HashSet<>();

    @OneToMany(mappedBy = "annee",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Set<Exam> examens=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;

    public Annee() {
    }

    public Annee(@NotNull String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public Set<Mois> getMois() {
        return mois;
    }

    public void setMois(Set<Mois> mois) {
        this.mois = mois;
    }

    @JsonIgnore
    public Set<Exam> getExamens() {
        return examens;
    }

    public void setExamens(Set<Exam> examens) {
        this.examens = examens;
    }

    @Override
    public String toString() {
        return "Annee{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
