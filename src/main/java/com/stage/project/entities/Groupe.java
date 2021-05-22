package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue( value="groupe" )
public class Groupe extends Activite {

    public Groupe() {
    }

    public Groupe(@NotNull String nom, @NotNull String description, @NotNull Boolean gratuit) {
        super(nom, description, gratuit);
    }

    @ManyToMany(mappedBy = "activitiesGrp",fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @OrderBy("numero ASC")
    private Set<Etudiant> etudiants=new HashSet<>();

    @JsonIgnore
    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
}
