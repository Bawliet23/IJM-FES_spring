package com.stage.project.entities.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stage.project.entities.Mois;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PaimentData {

    private Long id;
    @NotNull
    private float montantPayee;
    @NotNull
    private float montantRestant;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datePaiment;
    private String description;

    private Long etudiant_id;
    private Long activite_id;
    @NotNull
    private List<Mois> mois;

    public PaimentData() {
    }

    public PaimentData(@NotNull float montantPayee, @NotNull float montantRestant, @NotNull Date datePaiment,String description) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEtudiant_id() {
        return etudiant_id;
    }

    public void setEtudiant_id(Long etudiant_id) {
        this.etudiant_id = etudiant_id;
    }

    public Long getActivite_id() {
        return activite_id;
    }

    public void setActivite_id(Long activite_id) {
        this.activite_id = activite_id;
    }

    public List<Mois> getMois() {
        return mois;
    }

    public void setMois(List<Mois> mois) {
        this.mois = mois;
    }
}
