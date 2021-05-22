package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "typeetudiant")
@EntityListeners(AuditingEntityListener.class)
public class TypeEtudiant extends Auditable<String>{

    @OneToMany(mappedBy = "typeEtudiant",fetch = FetchType.LAZY)
    private Set<Activite> activites=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;

    public TypeEtudiant() {
    }

    public TypeEtudiant(@NotNull String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public Set<Activite> getActivites() {
        return activites;
    }

    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
    }

    @Override
    public String toString() {
        return "TypeEtudiant{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
