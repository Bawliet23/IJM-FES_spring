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
@Table(name = "jours")
@EntityListeners(AuditingEntityListener.class)
public class Jour extends Auditable<String>{

    @OneToMany(mappedBy = "jour",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Horraire> horraires=new HashSet<>();

    @Id
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;

    public Jour() {
    }

    public Jour(@NotNull String nom) {
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

    @Override
    public String toString() {
        return "Jour{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
