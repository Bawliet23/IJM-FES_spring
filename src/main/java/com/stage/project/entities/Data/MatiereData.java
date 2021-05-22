package com.stage.project.entities.Data;

import com.stage.project.entities.Niveau;

import java.util.List;
import java.util.Set;

public class MatiereData {

    private String nom;
    private List<Niveau>niveaux;

    public MatiereData() {
    }

    public MatiereData(String nom, List<Niveau> niveaux) {
        this.nom = nom;
        this.niveaux = niveaux;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Niveau> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }
}
