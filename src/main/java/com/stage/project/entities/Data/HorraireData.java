package com.stage.project.entities.Data;

public class HorraireData{

    private String demiHeure_id;
    private String jour_id;

    public HorraireData() {
    }

    public HorraireData(String demiHeure_id, String jour_id) {
        this.demiHeure_id = demiHeure_id;
        this.jour_id = jour_id;
    }

    public String getDemiHeure_id() {
        return demiHeure_id;
    }

    public void setDemiHeure_id(String demiHeure_id) {
        this.demiHeure_id = demiHeure_id;
    }

    public String getJour_id() {
        return jour_id;
    }

    public void setJour_id(String jour_id) {
        this.jour_id = jour_id;
    }
}
