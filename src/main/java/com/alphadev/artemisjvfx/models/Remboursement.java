package com.alphadev.artemisjvfx.models;

public class Remboursement {
    private  int id;
    private  double valeur;
    private  int duree;
    private  double valeur_tranche;
    private String etat;
    private int pret;
    private pret pret_id;

    public Remboursement(int id, double valeur, int duree, double valeur_tranche, String etat,  pret pret_id) {
        this.id = id;
        this.valeur = valeur;
        this.duree = duree;
        this.valeur_tranche = valeur_tranche;
        this.etat = etat;

        this.pret_id = pret_id;
    }

    public Remboursement(double valeur, int duree, double valeur_tranche, String etat, int pret, pret pret_id) {

        this.valeur = valeur;
        this.duree = duree;
        this.valeur_tranche = valeur_tranche;
        this.etat = etat;
        this.pret = pret;
        this.pret_id = pret_id;
    }

    public Remboursement() {

    }

    public Remboursement(int id) {
        this.id = id;
    }

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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getValeur_tranche() {
        return valeur_tranche;
    }

    public void setValeur_tranche(double valeur_tranche) {
        this.valeur_tranche = valeur_tranche;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public pret getPret_id() {
        return pret_id;
    }

    public void setPret_id(pret pret_id) {
        this.pret_id = pret_id;
    }

    public Remboursement( double valeur, int duree, double valeur_tranche, String etat, int pret) {

        this.valeur = valeur;
        this.duree = duree;
        this.valeur_tranche = valeur_tranche;
        this.etat = etat;
        this.pret = pret;

    }

    public Remboursement(int id, double valeur, int duree, double valeur_tranche, String etat, int pret) {
        this.id = id;
        this.valeur = valeur;
        this.duree = duree;
        this.valeur_tranche = valeur_tranche;
        this.etat = etat;
        this.pret = pret;

    }


}
