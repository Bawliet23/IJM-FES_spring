package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue( value="individu" )
public class Individu extends Activite{

    public Individu() {
    }

    public Individu(@NotNull String nom, @NotNull String description, @NotNull Boolean gratuit) {
        super(nom, description, gratuit);
    }

    @ManyToOne()
    @JoinColumn(name="etudiant_id")
    private Etudiant etudiant;

    @JsonIgnore
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
}
