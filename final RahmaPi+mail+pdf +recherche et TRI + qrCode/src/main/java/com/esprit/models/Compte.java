package com.esprit.models;

import java.util.Objects;

public class Compte {
    private int id;
    private String rib;
    private String type;
    private double solde;

    public Compte() {}

    public Compte(int id, String rib, String type, double solde) {
        this.id = id;
        this.rib = rib;
        this.type = type;
        this.solde = solde;
    }

    public Compte(String rib, String type, double solde) {
        this.rib = rib;
        this.type = type;
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte compte = (Compte) o;
        return id == compte.id &&
                Double.compare(compte.solde, solde) == 0 &&
                Objects.equals(rib, compte.rib) &&
                Objects.equals(type, compte.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rib, type, solde);
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", rib='" + rib + '\'' +
                ", type='" + type + '\'' +
                ", solde=" + solde +
                '}';
    }
}
