package com.stage.project.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "journalisation")
public class Journalisation implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "action")
	private String action;

	@Column(name = "model",length = 2000)
	private String model;

	@Column(name = "user")
	private String user;

	@Column(name = "date")
    @Temporal(TIMESTAMP)
    protected Date date;

	@Column(name = "oldvalue",length = 2000)
	private String oldvalue;

	@Column(name = "newValue",length = 2000)
	private String newValue;

    public Journalisation() {
    }

    public Journalisation(String action, String model, String user, Date date) {
        this.action = action;
        this.model = model;
        this.user = user;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
