package com.alphadev.artemisjvfx.models;

import java.util.ArrayList;
import java.util.List;

public class Portefeuille {
    private int id;
    private String nomPortefeuille;
    private double valeurTotale;

    public Portefeuille(int id, String nomPortefeuille, double valeurTotale) {
        this.id = id;
        this.nomPortefeuille = nomPortefeuille;
        this.valeurTotale = valeurTotale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomPortefeuille() {
        return nomPortefeuille;
    }

    public void setNomPortefeuille(String nomPortefeuille) {
        this.nomPortefeuille = nomPortefeuille;
    }

    public double getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(double valeurTotale) {
        this.valeurTotale = valeurTotale;
    }
    @Override
    public String toString() {
        return "portefeuille{" +
                "id=" + id +
                ", valeurtotale=" + valeurTotale +
                ", nomportefeuille='" + nomPortefeuille + '\'' +

              //  ", user_id=" + user_id +
                '}';
    }
    private List<Actions> actions;

    public Portefeuille() {
        this.actions = new ArrayList<>();
    }

    public void addAction(Actions action) {
        actions.add(action);
    }

}
