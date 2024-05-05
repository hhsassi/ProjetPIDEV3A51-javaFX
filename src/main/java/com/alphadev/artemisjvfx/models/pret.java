package com.alphadev.artemisjvfx.models;

public class pret {
   private int id;
   private double valeur;
    private String motif;
    private int salaire;
    private int garantie;
    private   String valeur_garantie;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public String getMotif() {
        return motif;
    }

    public pret(int id) {
        this.id = id;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public int getGarantie() {
        return garantie;
    }

    public void setGarantie(int garantie) {
        this.garantie = garantie;
    }

    public String getValeur_garantie() {
        return valeur_garantie;
    }

    public void setValeur_garantie(String valeur_garantie) {
        this.valeur_garantie = valeur_garantie;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public pret( double valeur, String motif, int salaire, int garantie, String valeur_garantie, int user_id) {
        this.valeur = valeur;
        this.motif = motif;
        this.salaire = salaire;
        this.garantie = garantie;
        this.valeur_garantie = valeur_garantie;
        this.user_id = user_id;
    }

    public pret(int id, double valeur, String motif, int salaire, int garantie, String valeur_garantie, int user_id) {
        this.id = id;
        this.valeur = valeur;
        this.motif = motif;
        this.salaire = salaire;
        this.garantie = garantie;
        this.valeur_garantie = valeur_garantie;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "pret{" +
                "id=" + id +
                ", valeur=" + valeur +
                ", motif='" + motif + '\'' +
                ", salaire=" + salaire +
                ", garantie=" + garantie +
                ", valeur_garantie='" + valeur_garantie + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
