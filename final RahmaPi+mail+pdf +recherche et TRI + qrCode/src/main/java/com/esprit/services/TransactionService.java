package com.esprit.services;

import com.esprit.models.Transaction;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements IService<Transaction> {

    Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Transaction transaction) {
        String req = "INSERT INTO `transaction` (`compte_id`, `montant_t`, `date_t`, `rib_dest`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, transaction.getCompteId());
            ps.setDouble(2, transaction.getMontant());
            ps.setDate(3, Date.valueOf(transaction.getDate()    ));
            ps.setString(4, transaction.getRibDest());
            ps.executeUpdate();
            System.out.println("Transaction added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding transaction: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Transaction transaction) {
        String req = "UPDATE `transaction` SET `compte_id` = ?, `montant_t` = ?, `date_t` = ?, `rib_dest` = ? WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, transaction.getCompteId());
            ps.setDouble(2, transaction.getMontant());
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.setString(4, transaction.getRibDest());
            ps.setInt(5, transaction.getId());

            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Transaction with ID: " + transaction.getId() + " has been updated.");
            } else {
                System.out.println("No Transaction found with ID: " + transaction.getId() + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating transaction: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `transaction` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Transaction with ID: " + id + " has been deleted successfully.");
            } else {
                System.out.println("No Transaction found with ID: " + id + ". Nothing deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting transaction: " + e.getMessage());
        }
    }

    @Override
    public Transaction getOneById(int id) {
        String req = "SELECT `id`, `compte_id`, `montant_t`, `date_t`, `rib_dest` FROM `transaction` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return new Transaction(
                        res.getInt("id"),
                        res.getInt("compte_id"),
                        res.getDouble("montant_t"),
                        res.getDate("date_t").toLocalDate(),
                        res.getString("rib_dest")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transaction by ID: " + e.getMessage());
        }
        return null; // Transaction not found
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        String req = "SELECT `id`, `compte_id`, `montant_t`, `date_t`, `rib_dest` FROM `transaction`";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                Transaction transaction = new Transaction(
                        res.getInt("id"),
                        res.getInt("compte_id"),
                        res.getDouble("montant_t"),
                        res.getDate("date_t").toLocalDate(),
                        res.getString("rib_dest")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
        return transactions;
    }
}
