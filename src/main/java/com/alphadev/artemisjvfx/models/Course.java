package com.alphadev.artemisjvfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty level = new SimpleStringProperty();
    private SimpleStringProperty file = new SimpleStringProperty();
    private SimpleIntegerProperty certificationId = new SimpleIntegerProperty();
    private SimpleStringProperty certificationName = new SimpleStringProperty(); // New property

    public String getCertificationName() {
        return certificationName.get();
    }

    public SimpleStringProperty certificationNameProperty() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName.set(certificationName);
    }

    public Course() {
    }

    public Course(SimpleIntegerProperty id, SimpleStringProperty title, SimpleStringProperty description, SimpleStringProperty level, SimpleStringProperty file, SimpleIntegerProperty certificationId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.file = file;
        this.certificationId = certificationId;
    }

    public Course(SimpleStringProperty title, SimpleStringProperty description, SimpleStringProperty level, SimpleStringProperty file, SimpleIntegerProperty certificationId) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.file = file;
        this.certificationId = certificationId;
    }

    public Course(String title, String description, String level, String file, Integer certId) {
        this.title.set(title);
        this.description.set(description);
        this.level.set(level);
        this.file.set(file);
        this.certificationId.set(certId);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLevel() {
        return level.get();
    }

    public SimpleStringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getFile() {
        return file.get();
    }

    public SimpleStringProperty fileProperty() {
        return file;
    }

    public void setFile(String file) {
        this.file.set(file);
    }

    public int getCertificationId() {
        return certificationId.get();
    }

    public SimpleIntegerProperty certificationIdProperty() {
        return certificationId;
    }

    public void setCertificationId(int certificationId) {
        this.certificationId.set(certificationId);
    }
}
