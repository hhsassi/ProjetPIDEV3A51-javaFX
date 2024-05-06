package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Actions;
import com.alphadev.artemisjvfx.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ActionsService implements CRUD<Actions> {

    private Connection cnx;

    public ActionsService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void add(Actions action) throws SQLException {
        String req = "INSERT INTO actions (id, symbol, valeur_achat, qte, date, id_portfolio) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, action.getId());
            ps.setString(2, action.getSymbol());
            ps.setDouble(3, action.getValeurAchat());
            ps.setInt(4, action.getQte());
            ps.setDate(5, new java.sql.Date(action.getDate().getTime())); // Assuming date is a java.util.Date
            ps.setInt(6, action.getIdPortfolio());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Action added successfully");
            } else {
                System.out.println("Failed to add action");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding action: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public void delete(Actions action) throws SQLException {
        String req = "DELETE FROM actions WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, action.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Action deleted successfully");
            } else {
                System.out.println("Action not found or delete failed");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting action: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public void update(Actions action) throws SQLException {
        String req = "UPDATE actions " +
                "SET symbol = ?, valeur_achat = ?, qte = ?, date = ?, id_portfolio = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, action.getSymbol());
            ps.setDouble(2, action.getValeurAchat());
            ps.setInt(3, action.getQte());
            ps.setDate(4, new java.sql.Date(action.getDate().getTime())); // Assuming date is a java.util.Date
            ps.setInt(5, action.getIdPortfolio());
            ps.setInt(6, action.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Action updated successfully");
            } else {
                System.out.println("Action not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating action: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }

    @Override
    public ResultSet selectAll() throws SQLException {
        String req = "SELECT * FROM actions";
        PreparedStatement ps = cnx.prepareStatement(req);
        return ps.executeQuery();
    }

    public ObservableList<Actions> getAll() throws SQLException {
        ObservableList<Actions> actionsList = FXCollections.observableArrayList();
        String req = "SELECT * FROM actions";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actions action = new Actions(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getDouble("valeur_achat"),
                        rs.getInt("qte"),
                        rs.getDate("date"), // Assuming date is stored as SQL Date
                        rs.getInt("id_portfolio")
                );
                actionsList.add(action);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while getting all actions: " + e.getMessage());
            throw e;
        }

        return actionsList;
    }
    public Actions searchBySymbol(String symbol) throws SQLException {
        Actions action = null;
        String query = "SELECT * FROM actions WHERE symbol = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, symbol);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                action = new Actions(
                        resultSet.getInt("id"),
                        resultSet.getString("symbol"),
                        resultSet.getDouble("valeur_achat"),
                        resultSet.getInt("qte"),
                        resultSet.getDate("date"),
                        resultSet.getInt("id_portfolio")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while searching for action by symbol: " + e.getMessage());
            throw e;
        }

        return action;
    }
    public int getTotalActionsCount() throws SQLException {
        String req = "SELECT COUNT(*) AS total FROM actions";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while getting total actions count: " + e.getMessage());
            throw e;
        }
        return 0; // Return 0 if no count is retrieved
    }

    public ObservableList<Actions> getActionsForPage(int pageIndex, int itemsPerPage) throws SQLException {
        ObservableList<Actions> actionsList = FXCollections.observableArrayList();
        String req = "SELECT * FROM actions LIMIT ? OFFSET ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, itemsPerPage);
            ps.setInt(2, pageIndex * itemsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actions action = new Actions(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getDouble("valeur_achat"),
                        rs.getInt("qte"),
                        rs.getDate("date"), // Assuming date is stored as SQL Date
                        rs.getInt("id_portfolio")
                );
                actionsList.add(action);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while getting actions for page: " + e.getMessage());
            throw e;
        }
        return actionsList;
    }

}
