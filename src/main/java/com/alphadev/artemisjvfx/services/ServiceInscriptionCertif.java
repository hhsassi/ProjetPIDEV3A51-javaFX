package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.InscriptionCertif;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ServiceInscriptionCertif {
    private Connection  cnx ;

    public ServiceInscriptionCertif() {
        cnx = DBConnection.getInstance().getCnx();
    }


    public ObservableList<String> getAllCertificationNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        String query = "SELECT nom_certif FROM certification";
        try (PreparedStatement pst = cnx.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("nom_certif"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching certification names: " + e.getMessage());
        }
        return names;
    }

    // Fetch all enrollments
    public ObservableList<InscriptionCertif> getAllEnrollments() {
        ObservableList<InscriptionCertif> enrollments = FXCollections.observableArrayList();
        String query = "SELECT ic.id, u.*, c.nom_certif FROM inscription_certif ic JOIN user u ON ic.user_id = u.id JOIN certification c ON ic.certification_id = c.id";
        try (PreparedStatement pst = cnx.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("email"), rs.getString("roles"), rs.getString("password"),
                        rs.getString("cin"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("num_tel"), rs.getString("adress"), rs.getDate("dob"),
                        null, rs.getInt("is_verified")
                );
                enrollments.add(new InscriptionCertif(
                        rs.getInt("id"), user, rs.getString("nom_certif")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching enrollments: " + e.getMessage());
        }
        return enrollments;
    }


    public String addEnrollmentByAddress(String userAddress, String certName) {
        if (!isValidEmail(userAddress) || !userExists(userAddress)) {
            return "Invalid user address or non-existent user!";
        }

        String userIdQuery = "SELECT id FROM user WHERE email = ?";
        String certIdQuery = "SELECT id FROM certification WHERE nom_certif = ?";
        String insertQuery = "INSERT INTO inscription_certif (user_id, certification_id) VALUES (?, ?)";

        try {
            // Check and potentially re-establish the connection
            if (cnx == null || cnx.isClosed()) {
                cnx = DBConnection.getInstance().getCnx();
            }

            cnx.setAutoCommit(false); // Start transaction

            try (PreparedStatement pstUser = cnx.prepareStatement(userIdQuery);
                 PreparedStatement pstCert = cnx.prepareStatement(certIdQuery)) {

                pstUser.setString(1, userAddress);
                ResultSet rsUser = pstUser.executeQuery();
                int userId = rsUser.next() ? rsUser.getInt("id") : -1;

                pstCert.setString(1, certName);
                ResultSet rsCert = pstCert.executeQuery();
                int certId = rsCert.next() ? rsCert.getInt("id") : -1;

                if (userId == -1 || certId == -1) {
                    cnx.rollback();
                    return "Invalid user address or certification!";
                }

                if (enrollmentExists(userId, certId)) {
                    cnx.rollback();
                    return "Enrollment already exists.";
                }

                try (PreparedStatement pstInsert = cnx.prepareStatement(insertQuery)) {
                    pstInsert.setInt(1, userId);
                    pstInsert.setInt(2, certId);
                    int rowsAffected = pstInsert.executeUpdate();
                    if (rowsAffected > 0) {
                        cnx.commit();
                        return "Enrollment added successfully.";
                    } else {
                        cnx.rollback();
                        return "Failed to add enrollment.";
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in enrollment operation: " + e.getMessage());
            if (cnx != null) {
                try {
                    cnx.rollback();
                } catch (SQLException ex) {
                    System.err.println("Transaction rollback failed: " + ex.getMessage());
                }
            }
            return "Database error during enrollment addition.";
        } finally {
            try {
                if (cnx != null) {
                    cnx.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }


    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error verifying user existence: " + e.getMessage());
        }
        return false;
    }
    private boolean enrollmentExists(int userId, int certId) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM inscription_certif WHERE user_id = ? AND certification_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(checkQuery)) {
            pst.setInt(1, userId);
            pst.setInt(2, certId);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }

}
    public String updateEnrollment(int enrollmentId, String newAddress, String newCertName) {
        String updateQuery = "UPDATE inscription_certif SET user_id = (SELECT id FROM user WHERE email = ?), certification_id = (SELECT id FROM certification WHERE nom_certif = ?) WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(updateQuery)) {
            pst.setString(1, newAddress);
            pst.setString(2, newCertName);
            pst.setInt(3, enrollmentId);
            int result = pst.executeUpdate();
            if (result > 0) {
                return "Enrollment updated successfully.";
            } else {
                return "Failed to update enrollment.";
            }
        } catch (SQLException e) {
            System.err.println("Error updating enrollment: " + e.getMessage());
            return "Database error during enrollment update.";
        }
    }

    public String deleteEnrollment(int enrollmentId) {
        String deleteQuery = "DELETE FROM inscription_certif WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(deleteQuery)) {
            pst.setInt(1, enrollmentId);
            int result = pst.executeUpdate();
            if (result > 0) {
                return "Enrollment deleted successfully.";
            } else {
                return "Failed to delete enrollment.";
            }
        } catch (SQLException e) {
            System.err.println("Error deleting enrollment: " + e.getMessage());
            return "Database error during enrollment deletion.";
        }
    }


}
