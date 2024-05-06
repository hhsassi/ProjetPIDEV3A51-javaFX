package com.alphadev.artemisjvfx.models;


import java.util.Date;

public class Actions {
    private int id;

    private String symbol;
    private double valeurAchat;
    private int qte;
    private Date date;
    private int idPortfolio;

    public Actions(int id,String symbol,  double valeurAchat, int qte, Date date, int idPortfolio) {
        this.id = id;
        this.symbol = symbol;
        this.valeurAchat = valeurAchat;
        this.qte = qte;
        this.date = date;
        this.idPortfolio = idPortfolio;
    }
    public Actions(String symbol, int qte) {
        this.symbol = symbol;
        this.qte = qte;
    }


    public String getSymbol() {
        return symbol;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }



    public double getValeurAchat() {
        return valeurAchat;
    }

    public void setValeurAchat(double valeurAchat) {
        this.valeurAchat = valeurAchat;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdPortfolio() {
        return idPortfolio;
    }

    public void setIdPortfolio(int idPortfolio) {
        this.idPortfolio = idPortfolio;
    }

    @Override
    public String toString() {
        return "Actions{" +
                "symbol='" + symbol + '\'' +
                ", valeurAchat=" + valeurAchat +
                ", qte=" + qte +
                ", date=" + date +
                ", idPortfolio=" + idPortfolio +
                '}';
    }
    private int selectedQuantity;

    public Actions( int selectedQuantity) {
        // Initialize other fields...
        this.selectedQuantity = selectedQuantity;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
}
