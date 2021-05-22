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
@Table(name = "seances")
@EntityListeners(AuditingEntityListener.class)
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="activite_id",nullable = false)
    private Activite activite;

    @OneToMany(mappedBy = "seance",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Presence> presences=new HashSet<>();

    @NotNull
    private String description;
    @NotNull
    private Timestamp date;
    private String hourEnd;
    public Seance(){}

    public Seance(@NotNull String description, @NotNull Timestamp date) {
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    @JsonIgnore
    public Set<Presence> getPresences() {
        return presences;
    }

    public void setPresences(Set<Presence> presences) {
        this.presences = presences;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }
}
