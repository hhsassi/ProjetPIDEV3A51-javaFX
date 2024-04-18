package com.alphadev.artemisjvfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class Certif {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty nom_certif = new SimpleStringProperty();
    private SimpleStringProperty badge_certif = new SimpleStringProperty();
    private Image badgeImage;
    private SimpleStringProperty description_certif = new SimpleStringProperty();
    private SimpleIntegerProperty duree_certif = new SimpleIntegerProperty();
    private SimpleIntegerProperty niveau_certif = new SimpleIntegerProperty();

    public Certif(int id, String nom, String badgePath, String description, int duree, int niveau) {
        this.id.set(id);
        this.nom_certif.set(nom);
        this.badge_certif.set(badgePath);
        this.description_certif.set(description);
        this.duree_certif.set(duree);
        this.niveau_certif.set(niveau);
        this.badgeImage = new Image("file:src/main/resources/" + badgePath, true); // Load image asynchronously
    }
    public Certif(String nom, String badgePath, String description, int duree, int niveau) {
        this.nom_certif.set(nom);
        this.badge_certif.set(badgePath);
        this.description_certif.set(description);
        this.duree_certif.set(duree);
        this.niveau_certif.set(niveau);
        this.badgeImage = new Image("file:src/main/resources/" + badgePath, true); // Load image asynchronously
    }

    // Getters for JavaFX TableView to use Properties
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nom_certifProperty() {
        return nom_certif;
    }

    public Image getBadgeImage() {

        return badgeImage;
    }

    public SimpleStringProperty description_certifProperty() {
        return description_certif;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom_certif() {
        return nom_certif.get();
    }

    public void setNom_certif(String nom_certif) {
        this.nom_certif.set(nom_certif);
    }

    public String getBadge_certif() {
        return badge_certif.get();
    }

    public SimpleStringProperty badge_certifProperty() {
        return badge_certif;
    }

    public void setBadge_certif(String badge_certif) {
        this.badge_certif.set(badge_certif);
    }

    public void setBadgeImage(Image badgeImage) {
        this.badgeImage = badgeImage;
    }

    public String getDescription_certif() {
        return description_certif.get();
    }

    public void setDescription_certif(String description_certif) {
        this.description_certif.set(description_certif);
    }

    public int getDuree_certif() {
        return duree_certif.get();
    }

    public void setDuree_certif(int duree_certif) {
        this.duree_certif.set(duree_certif);
    }

    public int getNiveau_certif() {
        return niveau_certif.get();
    }

    public void setNiveau_certif(int niveau_certif) {
        this.niveau_certif.set(niveau_certif);
    }

    public SimpleIntegerProperty duree_certifProperty() {
        return duree_certif;
    }

    public SimpleIntegerProperty niveau_certifProperty() {
        return niveau_certif;
    }
}
