package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Actions;
import com.alphadev.artemisjvfx.models.Portefeuille;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class PortfolioService implements CRUD<Portefeuille> {

    private Connection cnx;
    private List<Actions> purchasedActions;
    public PortfolioService() {
        cnx = DBConnection.getInstance().getCnx();
        purchasedActions = new ArrayList<>();
    }


    @Override
    public void add(Portefeuille portefeuille) throws SQLException {
        String req = "INSERT INTO portefeuille (id, nom_portefeuille, valeur_totale) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, portefeuille.getId());
            ps.setString(2, portefeuille.getNomPortefeuille());
            ps.setDouble(3, portefeuille.getValeurTotale());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Portefeuille added successfully");
            } else {
                System.out.println("Failed to add portefeuille");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding portefeuille: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public void delete(Portefeuille portefeuille) throws SQLException {
        String req = "DELETE FROM portefeuille WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, portefeuille.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Portefeuille deleted successfully");
            } else {
                System.out.println("Portefeuille not found or delete failed");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting portefeuille: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }    }

    @Override
    public void update(Portefeuille portefeuille) throws SQLException {
        String req = "UPDATE portefeuille " +
                "SET nom_portefeuille = ?, valeur_totale = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, portefeuille.getNomPortefeuille());
            ps.setDouble(2, portefeuille.getValeurTotale());
            ps.setInt(3, portefeuille.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Portefeuille updated successfully");
            } else {
                System.out.println("Portefeuille not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating portefeuille: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }    }

    @Override
    public ResultSet selectAll() throws SQLException {
        String req = "SELECT * FROM portefeuille";
        PreparedStatement ps = cnx.prepareStatement(req);
        return ps.executeQuery();

    }
    public ObservableList<Portefeuille> getAll() throws SQLException {
        ObservableList<Portefeuille> portefeuilles = FXCollections.observableArrayList(); // Create an ObservableList
        String req = "SELECT * FROM portefeuille";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Portefeuille portefeuille = new Portefeuille(
                        rs.getInt("id"),
                        rs.getString("nom_portefeuille"),
                        rs.getDouble("valeur_totale")
                );
                portefeuilles.add(portefeuille); // Add Portefeuille objects to the ObservableList
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while getting all portefeuilles: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }

        return portefeuilles;
}
public List<Integer> getAllPortfolioIds() throws SQLException {
        List<Integer> portfolioIds = new ArrayList<>();
        String query = "SELECT id FROM portefeuille"; // Adjust the table name according to your database schema

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                portfolioIds.add(id);
            }
        }

        return portfolioIds;
    }
    public void addActionToPortfolio(Actions action, int quantity) {
        // Set the selected quantity for the action
        action.setSelectedQuantity(quantity);

        // Add the purchased action to the list only once
        purchasedActions.add(action);
    }



    // Getter for the list of purchased actions
    public List<Actions> getPurchasedActions() {
        return purchasedActions;
    }

}
