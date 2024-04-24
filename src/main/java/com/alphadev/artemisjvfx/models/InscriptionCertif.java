package com.alphadev.artemisjvfx.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InscriptionCertif {
    private int id;
    private User user;  // Using the User object
    private StringProperty certificationName = new SimpleStringProperty();

    public InscriptionCertif(int id, User user, String certificationName) {
        this.id = id;
        this.user = user;
        this.certificationName.set(certificationName);
    }

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // User
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Certification Name
    public String getCertificationName() {
        return certificationName.get();
    }

    public void setCertificationName(String certificationName) {
        this.certificationName.set(certificationName);
    }

    public StringProperty certificationNameProperty() {
        return certificationName;
    }
}
