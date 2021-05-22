package com.stage.project.entities.Data;

public class NoteData {

    private int etudiant_id;
    private String note;
    private String remarque;
    private String decision;
    private Long note_id;
    public NoteData() {
    }

    public NoteData(int etudiant_id, String note, String remarque, String decision) {
        this.etudiant_id = etudiant_id;
        this.note = note;
        this.remarque = remarque;
        this.decision = decision;
    }

    public int getEtudiant_id() {
        return etudiant_id;
    }

    public void setEtudiant_id(int etudiant_id) {
        this.etudiant_id = etudiant_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Long getNote_id() {
        return note_id;
    }

    public void setNote_id(Long note_id) {
        this.note_id = note_id;
    }
}
