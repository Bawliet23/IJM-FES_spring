package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paiments")
@EntityListeners(AuditingEntityListener.class)
public class Paiment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="etudiant_id",nullable = false)
    private Etudiant etudiant;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "paiment_mois",
            joinColumns = { @JoinColumn(name = "paiment_id") },
            inverseJoinColumns = { @JoinColumn(name = "mois_id") })
    private Set<Mois> mois =new HashSet<>();

    @ManyToOne()
    @JoinColumn(name="activite_id")
    private Activite activite;

    @NotNull
    private float montantPayee;
    @NotNull
    private float montantRestant;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.DATE)
    private Date datePaiment;
    private String description;
    @Transient
    @JsonSerialize
    private String activite_nom;
    @Transient
    private String dateString;

    public Paiment() {
    }

    public Paiment(@NotNull float montantPayee, @NotNull float montantRestant, @NotNull Date datePaiment,String description) {
        this.montantPayee = montantPayee;
        this.montantRestant = montantRestant;
        this.datePaiment = datePaiment;
        this.description=description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMontantPayee() {
        return montantPayee;
    }

    public void setMontantPayee(float montantPayee) {
        this.montantPayee = montantPayee;
    }

    public float getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(float montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Date getDatePaiment() {
        return datePaiment;
    }

    public void setDatePaiment(Date datePaiment) {
        this.datePaiment = datePaiment;
    }

    @JsonIgnore
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @JsonIgnore
    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Set<Mois> getMois() {
        return mois;
    }

    public void setMois(Set<Mois> mois) {
        this.mois = mois;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivite_nom() {
        return activite_nom;
    }

    public void setActivite_nom(String activite_nom) {
        this.activite_nom = activite_nom;
    }

    public String getdateString() {
        return dateString;
    }

    public void setdateStringString (String dateString) {
        this.dateString = dateString;
    }

    @PostLoad
    public void setActivite_nom(){
         this.activite_nom=this.activite.getNom();
         DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
         this.dateString=dateFormat.format(this.datePaiment);  ;
    }

    @Override
    public String toString() {
        return "Paiment{" +
                "id=" + id +
                '}';
    }
}
