package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mois")
@EntityListeners(AuditingEntityListener.class)
public class Mois extends Auditable<String>{

    @ManyToOne()
    @JoinColumn(name="annee_id",nullable = false)
    private Annee annee;

    @ManyToMany(mappedBy = "mois")
    private Set<Paiment> paiments=new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    public Mois() {
    }

    public Mois(@NotNull String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public Set<Paiment> getPaiments() {
        return paiments;
    }

    public void setPaiments(Set<Paiment> paiments) {
        this.paiments = paiments;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Mois{" +
                "annee=" + annee +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
