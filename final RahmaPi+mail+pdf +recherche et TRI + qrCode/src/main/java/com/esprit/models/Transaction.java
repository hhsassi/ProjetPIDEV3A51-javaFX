package com.esprit.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private int id;
    private int compteId; // Associated account ID
    private double montant; // Transaction amount
    private LocalDate date; // Transaction date and time
    private String ribDest; // Destination account RIB

    public Transaction() {}

    public Transaction(int id, int compteId, double montant, LocalDate date, String ribDest) {
        this.id = id;
        this.compteId = compteId;
        this.montant = montant;
        this.date = date;
        this.ribDest = ribDest;
    }

    public Transaction(double montant, LocalDate value, String ribDest, int id) {
        this.compteId = id;
        this.montant = montant;
        this.date = value;
        this.ribDest = ribDest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRibDest() {
        return ribDest;
    }

    public void setRibDest(String ribDest) {
        this.ribDest = ribDest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                compteId == that.compteId &&
                Double.compare(that.montant, montant) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(ribDest, that.ribDest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compteId, montant, date, ribDest);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", compteId=" + compteId +
                ", montant=" + montant +
                ", date=" + date +
                ", ribDest='" + ribDest + '\'' +
                '}';
    }
}
