package com.alphadev.artemisjvfx.models;


public class Certif {
    private String  nom_certif;
    private String bage_certif;
    private String description_certif;
    private Integer duree_certif;
    private Integer niveau_certif;

    public Certif(String nom_certif, String bage_certif, String description_certif, Integer duree_certif, Integer niveau_certif) {
        this.nom_certif = nom_certif;
        this.bage_certif = bage_certif;
        this.description_certif = description_certif;
        this.duree_certif = duree_certif;
        this.niveau_certif = niveau_certif;
    }

    public String getNom_certif() {
        return nom_certif;
    }

    public void setNom_certif(String nom_certif) {
        this.nom_certif = nom_certif;
    }

    public String getBage_certif() {
        return bage_certif;
    }

    public void setBage_certif(String bage_certif) {
        this.bage_certif = bage_certif;
    }

    public String getDescription_certif() {
        return description_certif;
    }

    public void setDescription_certif(String description_certif) {
        this.description_certif = description_certif;
    }

    public Integer getDuree_certif() {
        return duree_certif;
    }

    public void setDuree_certif(Integer duree_certif) {
        this.duree_certif = duree_certif;
    }

    public Integer getNiveau_certif() {
        return niveau_certif;
    }

    public void setNiveau_certif(Integer niveau_certif) {
        this.niveau_certif = niveau_certif;
    }

    @Override
    public String toString() {
        return nom_certif;
    }
}
