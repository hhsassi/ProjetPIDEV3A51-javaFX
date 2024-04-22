package com.alphadev.artemisjvfx.services;


import com.alphadev.artemisjvfx.utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.alphadev.artemisjvfx.models.Certif;
public class ServiceCertif {
    final private Connection cnx;

    public ServiceCertif() {
        {
            cnx = DBConnection.getInstance().getCnx();
        }
    }
    public void addCertif(Certif certif) {
        String query = "INSERT INTO certification (nom_certif, badge_certif, description_certif, duree_certif, niveau_certif) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, certif.getNom_certif());
            pst.setString(2, certif.getBadge_certif());
            pst.setString(3, certif.getDescription_certif());
            pst.setInt(4, certif.getDuree_certif());
            pst.setInt(5, certif.getNiveau_certif());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Certification added successfully");
            } else {
                System.out.println("Failed to add certification");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding certification: " + e.getMessage());
        }
    }
    public List<Certif> getAllCertifs() {
        List<Certif> certifs = new ArrayList<>();
        String query = "SELECT id, nom_certif, badge_certif, description_certif, duree_certif, niveau_certif FROM certification";
        try (PreparedStatement pst = cnx.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Certif certif = new Certif(
                        rs.getInt("id"),
                        rs.getString("nom_certif"),
                        rs.getString("badge_certif"),
                        rs.getString("description_certif"),
                        rs.getInt("duree_certif"),
                        rs.getInt("niveau_certif")
                );
                certifs.add(certif);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching certifications: " + e.getMessage());
        }
        return certifs;
    }
    public void updateCertif(Certif certif) {
        String query = "UPDATE certification SET nom_certif = ?, badge_certif = ?, description_certif = ?, duree_certif = ?, niveau_certif = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, certif.getNom_certif());
            pst.setString(2, certif.getBadge_certif());
            pst.setString(3, certif.getDescription_certif());
            pst.setInt(4, certif.getDuree_certif());
            pst.setInt(5, certif.getNiveau_certif());
            pst.setInt(6, certif.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred while updating certification: " + e.getMessage());
        }
    }
    public boolean canDeleteCertif(int certifId) {
        String query = "SELECT COUNT(*) FROM cours WHERE certification_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, certifId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true if no courses linked, false otherwise
            }
        } catch (SQLException e) {
            System.out.println("Error checking for dependent courses: " + e.getMessage());
        }
        return false; // Assume cannot delete if error occurs
    }

    public void deleteCertif(int certifId) {
        if (!canDeleteCertif(certifId)) {
            System.out.println("Cannot delete certification as it is linked to existing courses.");
            return; // Stop the deletion process
        }

        String query = "DELETE FROM certification WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, certifId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Certification deleted successfully");
            } else {
                System.out.println("No such certification exists or failed to delete certification.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting certification: " + e.getMessage());
        }
    }



}
