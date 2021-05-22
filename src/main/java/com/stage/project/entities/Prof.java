package com.stage.project.entities;

import com.fasterxml.jackson.annotation.*;
import com.stage.project.entities.Auditing.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professeurs")
@EntityListeners(AuditingEntityListener.class)
public class Prof extends Auditable<String> {

    @OneToMany(mappedBy = "prof",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OrderBy("jour ASC,demiHeure ASC")
    private Set<Horraire> horraires=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "profs")
    private Set<LigneNvMatiere> ligneNvMatieres=new HashSet<>();

    @OneToOne(mappedBy = "prof",cascade = CascadeType.ALL)
    private User user;

    @Id
    @Column(name = "cin", nullable = false,unique = true)
    private String cin;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    @Column(columnDefinition = "DATE default '1992-06-04'")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private int age;
    private String adresse;
    private String tele;
    private String email;
    @NotNull
    private String diplomes;

    public Prof() {
    }

    public Prof(@NotNull String cin, @NotNull String nom, @NotNull String prenom, @NotNull Date dateNaissance, String adresse, String tele, String email, @NotNull String diplomes) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.tele = tele;
        this.email = email;
        this.diplomes = diplomes;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }
 public void setDateNaissance(Date dateNaissance)
 {
     this.dateNaissance=dateNaissance;
 }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiplomes() {
        return diplomes;
    }

    public void setDiplomes(String diplomes) {
        this.diplomes = diplomes;
    }

    @JsonIgnore
    public Set<Horraire> getHorraires() {
        return horraires;
    }

    public void setHorraires(Set<Horraire> horraires) {
        this.horraires = horraires;
    }

    @JsonIgnore
    public Set<LigneNvMatiere> getLigneNvMatieres() {
        return ligneNvMatieres;
    }

    public void setLigneNvMatieres(Set<LigneNvMatiere> ligneNvMatieres) {
        this.ligneNvMatieres = ligneNvMatieres;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Prof{" +
                "cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
