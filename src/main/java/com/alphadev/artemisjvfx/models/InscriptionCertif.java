package com.alphadev.artemisjvfx.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InscriptionCertif {
    private int id;
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty certificationName = new SimpleStringProperty();

    public int getId() {
        return id;
    }

    public InscriptionCertif(int id, String userName, String certificationName) {
        this.id = id;
        this.userName.set(userName);
        this.certificationName.set(certificationName);
    }
    public InscriptionCertif(String userName, String certificationName) {
        this.setUserName(userName);
        this.setCertificationName(certificationName);
    }

    public final String getUserName() {
        return userName.get();
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public final String getCertificationName() {
        return certificationName.get();
    }

    public final void setCertificationName(String value) {
        certificationName.set(value);
    }

    public StringProperty certificationNameProperty() {
        return certificationName;
    }
}
