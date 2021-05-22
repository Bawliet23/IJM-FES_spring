package com.stage.project.entities.Data;

import com.stage.project.entities.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActiviteData {

    private Long id;
    private String nom;
    private String description;
    private Boolean gratuit;
    private String type;

    private TypeEtudiant typeEtudiant;
    private Integer niveau_id;
    private String matiere_id;
    private Salle salle;
    private List<HorraireData> horraires=new ArrayList<>();

    public ActiviteData() {
    }

    public ActiviteData(String nom,String description,Boolean gratuit,String type,Salle salle) {
        this.nom = nom;
        this.description = description;
        this.gratuit = gratuit;
        this.type=type;
        this.salle=salle;
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

    public Boolean getGratuit() {
        return gratuit;
    }

    public void setGratuit(Boolean gratuit) {
        this.gratuit = gratuit;
    }

    public Integer getNiveau_id() {
        return niveau_id;
    }

    public void setNiveau_id(Integer niveau_id) {
        this.niveau_id = niveau_id;
    }

    public String getMatiere_id() {
        return matiere_id;
    }

    public void setMatiere_id(String matiere_id) {
        this.matiere_id = matiere_id;
    }

    public List<HorraireData> getHorraires() {
        return horraires;
    }

    public void setHorraires(List<HorraireData> horraires) {
        this.horraires = horraires;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEtudiant getTypeEtudiant() {
        return typeEtudiant;
    }

    public void setTypeEtudiant(TypeEtudiant typeEtudiant) {
        this.typeEtudiant = typeEtudiant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
