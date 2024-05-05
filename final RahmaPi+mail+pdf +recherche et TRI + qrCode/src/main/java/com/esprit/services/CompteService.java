package com.esprit.services;

import com.esprit.models.Compte;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteService implements IService<Compte> {

    Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Compte compte) {
        String req = "INSERT INTO `compte` (`rib`, `type_c`, `solde_c`) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, compte.getRib());
            ps.setString(2, compte.getType());
            ps.setDouble(3, compte.getSolde());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                compte.setId(rs.getInt(1));
            }
            System.out.println("Compte added with ID: " + compte.getId());
        } catch (SQLException e) {
            System.out.println("Error adding compte: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Compte compte) {
        String req = "UPDATE `compte` SET `rib` = ?, `type_c` = ?, `solde_c` = ? WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, compte.getRib());
            ps.setString(2, compte.getType());
            ps.setDouble(3, compte.getSolde());
            ps.setInt(4, compte.getId());

            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Compte with ID: " + compte.getId() + " has been updated.");
            } else {
                System.out.println("No Compte found with ID: " + compte.getId() + ". Nothing updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Compte: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `compte` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Compte with ID: " + id + " has been deleted successfully.");
            } else {
                System.out.println("No Compte found with ID: " + id + ". Nothing deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Compte with ID: " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Compte getOneById(int id) {
        String req = "SELECT `id`, `rib`, `type_c`, `solde_c` FROM `compte` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return new Compte(
                        res.getInt("id"),
                        res.getString("rib"),
                        res.getString("type_c"),
                        res.getDouble("solde_c")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Compte by ID: " + e.getMessage());
        }
        return null; // Compte not found
    }

    @Override
    public List<Compte> getAll() {
        List<Compte> comptes = new ArrayList<>();
        String req = "SELECT `id`, `rib`, `type_c`, `solde_c` FROM `compte`";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                Compte compte = new Compte(
                        res.getInt("id"),
                        res.getString("rib"),
                        res.getString("type_c"),
                        res.getDouble("solde_c")
                );
                comptes.add(compte);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching comptes: " + e.getMessage());
        }
        return comptes;
    }
}
