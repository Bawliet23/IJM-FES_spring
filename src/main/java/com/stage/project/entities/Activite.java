package com.stage.project.entities;

import com.fasterxml.jackson.annotation.*;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "activites")
@EntityListeners(AuditingEntityListener.class)
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "type" )
public class Activite extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="salle_id")
    private Salle salle;

    @ManyToOne()
    @JoinColumn(name="typeEtudiant_id")
    private TypeEtudiant typeEtudiant;

    @ManyToOne()
    @JoinColumn(name="ligneNvMatiere_id")
    private LigneNvMatiere ligneNvMatiere;

    @OneToMany(mappedBy = "activite",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private Set<Seance> seances=new HashSet<>();

    @OneToMany(mappedBy = "activite",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Paiment>paiments=new HashSet<>();

    @OneToMany(mappedBy = "activite",cascade = CascadeType.PERSIST)
    @OrderBy("jour ASC,demiHeure ASC")
    private Set<Horraire> horraires=new HashSet<>();

    @NotNull
    private String nom;
    @NotNull
    private String description;
    @NotNull
    private Boolean gratuit;

    @Transient
    private String type;

    public Activite() {
    }

    public Activite(@NotNull String nom, @NotNull String description, @NotNull Boolean gratuit) {
        this.nom = nom;
        this.description = description;
        this.gratuit = gratuit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public TypeEtudiant getTypeEtudiant() {
        return typeEtudiant;
    }

    public void setTypeEtudiant(TypeEtudiant typeEtudiant) {
        this.typeEtudiant = typeEtudiant;
    }

    @JsonIgnore
    public Set<Paiment> getPaiments() {
        return paiments;
    }

    public void setPaiments(Set<Paiment> paiments) {
        this.paiments = paiments;
    }

    @JsonIgnore
    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @JsonIgnore
    public LigneNvMatiere getLigneNvMatiere() {
        return ligneNvMatiere;
    }

    public void setLigneNvMatiere(LigneNvMatiere ligneNvMatiere) {
        this.ligneNvMatiere = ligneNvMatiere;
    }

    @JsonIgnore
    public Set<Horraire> getHorraires() {
        return horraires;
    }

    public void setHorraires(Set<Horraire> horraires) {
        this.horraires = horraires;
    }

    public Boolean getGratuit() {
        return gratuit;
    }

    public void setGratuit(Boolean gratuit) {
        this.gratuit = gratuit;
    }

    @JsonIgnore
    public Set<Seance> getSeances() {
        return seances;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @PostLoad
    public void doStuff(){
         if (this instanceof Groupe)this.type="groupe";
         else this.type="individu";
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
