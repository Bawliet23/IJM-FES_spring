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
@Table(name = "niveaux")
@EntityListeners(AuditingEntityListener.class)
public class Niveau extends Auditable<String>{

    @OneToMany(mappedBy = "niveau",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<LigneNvMatiere> ligneNvMatieres=new HashSet<>();

    @Id
    @Column(name = "nv", nullable = false,unique = true)
    private int nv;

    public Niveau() {
    }

    public Niveau(@NotNull int nv) {
        this.nv = nv;
    }

    public int getNv() {
        return nv;
    }

    public void setNv(int nv) {
        this.nv = nv;
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
        return "Niveau{" +
                "nv=" + nv +
                '}';
    }
}
