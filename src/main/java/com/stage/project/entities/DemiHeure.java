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
@Table(name = "demiheures")
@EntityListeners(AuditingEntityListener.class)
public class DemiHeure extends Auditable<String>{

    @OneToMany(mappedBy = "demiHeure",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Horraire> horraires=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;
    @Transient
    private int heure;
    @Transient
    private int min;

    public DemiHeure() {
    }

    public DemiHeure(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public Set<Horraire> getHorraires() {
        return horraires;
    }

    public void setHorraires(Set<Horraire> horraires) {
        this.horraires = horraires;
    }

    @PostLoad
    public void setHeureAndMin(){
        String heure_string = this.nom.substring(0, 2);
        heure_string = heure_string.replaceAll("\\D", "");
        this.heure= Integer.parseInt(heure_string);
        String min_string = this.nom.substring(this.nom.length() - 2);
        this.min= Integer.parseInt(min_string);
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "DemiHeure{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
