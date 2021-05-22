package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "etudiants")
@EntityListeners(AuditingEntityListener.class)
public class Etudiant extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="type_id")
    private Type type;

    @OneToMany(mappedBy = "etudiant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("datePaiment DESC")
    private Set<Paiment>paiments=new HashSet<>();

    @OneToMany(mappedBy = "etudiant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("seance DESC")
    private Set<Presence>presences=new HashSet<>();

    @OneToMany(mappedBy = "etudiant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("exam DESC")
    private Set<Note>notes=new HashSet<>();

    @OneToMany(mappedBy = "etudiant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Individu> activitiesInd=new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "groupe_etudiant",
            joinColumns = { @JoinColumn(name = "etudiant_id") },
            inverseJoinColumns = { @JoinColumn(name = "groupe_id") })
    private Set<Groupe> activitiesGrp =new HashSet<>();

    @OneToOne(mappedBy = "etudiant",cascade = CascadeType.ALL)
    private User user;

    @NotNull
    @Column(unique=true)
    private int numero;
    @NotNull
    private String nom;
    @NotNull
    private String nomArabe;
    @NotNull
    private String prenom;
    @NotNull
    private String prenomArabe;
    @NotNull
    @Column(columnDefinition = "DATE default '1992-06-04'")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private int age;
    private String adresse;
    private String tele1;
    private String tele2;
    private String photo;
    private String metier;
    private String remarque;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateInscription;

    public Etudiant() {

    }

    public Etudiant(@NotNull int numero, @NotNull String nom, @NotNull String nomArabe, @NotNull String prenom, @NotNull String prenomArabe, @NotNull Date dateNaissance, String adresse, String tele1, String tele2, String metier, String remarque, @NotNull String photo, @NotNull Date dateInscription) {
        this.numero = numero;
        this.nom = nom;
        this.nomArabe = nomArabe;
        this.prenom = prenom;
        this.prenomArabe = prenomArabe;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.tele1 = tele1;
        this.remarque=remarque;
        this.metier=metier;
        this.tele2 = tele2;
        this.photo = photo;
        this.dateInscription = dateInscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomArabe() {
        return nomArabe;
    }

    public void setNomArabe(String nomArabe) {
        this.nomArabe = nomArabe;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenomArabe() {
        return prenomArabe;
    }

    public void setPrenomArabe(String prenomArabe) {
        this.prenomArabe = prenomArabe;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @JsonIgnore
    public Set<Paiment> getPaiments() {
        return paiments;
    }

    public void setPaiments(Set<Paiment> paiments) {
        this.paiments = paiments;
    }

    @JsonIgnore
    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @JsonIgnore
    public Set<Presence> getPresences() {
        return presences;
    }

    public void setPresences(Set<Presence> presences) {
        this.presences = presences;
    }

    @JsonIgnore
    public Set<Individu> getActivitiesInd() {
        return activitiesInd;
    }

    public void setActivitiesInd(Set<Individu> activitiesInd) {
        this.activitiesInd = activitiesInd;
    }

    @JsonIgnore
    public Set<Groupe> getActivitiesGrp() {
        return activitiesGrp;
    }

    public void setActivitiesGrp(Set<Groupe> activitiesGrp) {
        this.activitiesGrp = activitiesGrp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", numero=" + numero +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
