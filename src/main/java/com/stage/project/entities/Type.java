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
@Table(name = "type")
@EntityListeners(AuditingEntityListener.class)
public class Type extends Auditable<String>{

    @OneToMany(mappedBy = "type",fetch = FetchType.LAZY)
    private Set<Etudiant> etudiants=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;

    public Type() {
    }

    public Type(@NotNull String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @Override
    public String toString() {
        return "Type{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
