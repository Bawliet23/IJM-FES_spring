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
@Table(name = "lignenvmatiere")
@EntityListeners(AuditingEntityListener.class)
public class LigneNvMatiere extends Auditable<String>{

    @Id
    @Column(name = "id", nullable = false,unique = true)
    private String id;

    @ManyToOne()
    @JoinColumn(name="matiere_id",nullable = false)
    private Matiere matiere;

    @ManyToOne()
    @JoinColumn(name="niveau_id",nullable = false)
    private Niveau niveau;

    @OneToMany(mappedBy = "ligneNvMatiere",fetch = FetchType.LAZY)
    private Set<Activite> activites=new HashSet<>();

    @OneToMany(mappedBy = "ligneNvMatiere",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Exam> examens=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "lignenvmatiere_prof",
            joinColumns = { @JoinColumn(name = "ligneNvMatiere_id") },
            inverseJoinColumns = { @JoinColumn(name = "prof_id") })
    private Set<Prof> profs =new HashSet<>();

    public LigneNvMatiere(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @JsonIgnore
    public Set<Activite> getActivites() {
        return activites;
    }

    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
    }

    @JsonIgnore
    public Set<Prof> getProfs() {
        return profs;
    }

    public void setProfs(Set<Prof> profs) {
        this.profs = profs;
    }

    @JsonIgnore
    public Set<Exam> getExamens() {
        return examens;
    }

    public void setExamens(Set<Exam> examens) {
        this.examens = examens;
    }

    @Override
    public String toString() {
        return "LigneNvMatiere{" +
                "id='" + id + '\'' +
                '}';
    }
}
