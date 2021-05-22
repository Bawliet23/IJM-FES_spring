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
@Table(name = "matieres")
@EntityListeners(AuditingEntityListener.class)
public class Matiere extends Auditable<String> {

    @OneToMany(mappedBy = "matiere",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<LigneNvMatiere> ligneNvMatieres=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;
    private String description;
    @NotNull
    private float prixEnfant;
    @NotNull
    private float prixAdult;
    private String photo;

    public Matiere() {
    }

    public Matiere(@NotNull String nom, @NotNull String description, @NotNull float prixEnfant, @NotNull float prixAdult, @NotNull String photo) {
        this.nom = nom;
        this.description = description;
        this.prixEnfant = prixEnfant;
        this.prixAdult = prixAdult;
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrixEnfant() {
        return prixEnfant;
    }

    public void setPrixEnfant(float prixEnfant) {
        this.prixEnfant = prixEnfant;
    }

    public float getPrixAdult() {
        return prixAdult;
    }

    public void setPrixAdult(float prixAdult) {
        this.prixAdult = prixAdult;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonIgnore
    public Set<LigneNvMatiere> getLigneNvsMatieres() {
        return ligneNvMatieres;
    }

    public void setLigneNvsMatieres(Set<LigneNvMatiere> ligneNvMatieres) {
        this.ligneNvMatieres = ligneNvMatieres;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
