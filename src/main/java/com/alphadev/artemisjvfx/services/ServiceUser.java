package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServiceUser implements CRUD<User> {

private Connection cnx;

    public ServiceUser(){
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void add(User user) {
        String req = "INSERT INTO user (email, roles, password, cin, nom, prenom, num_tel, adress, dob, auth_code, is_verified) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, "[]"); // Assuming user.getRoles() returns a string representing roles
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getCin());
            ps.setString(5, user.getNom());
            ps.setString(6, user.getPrenom());
            ps.setString(7, user.getNum_tel());
            ps.setString(8, user.getAdress());
            ps.setDate(9, user.getDob());
            ps.setString(10,null); // null jusqua'a l'implementation du systeme de verification
            ps.setInt(11, 0); // 0 jusqu'a l'implementation du systeme de verification

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully");
            } else {
                System.out.println("Failed to add user");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding user: " + e.getMessage());
        }
    }


    @Override
    public void delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE email = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, user.getEmail());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found or failed to delete");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting user: " + e.getMessage());
        }
    }




    @Override
    public void update(User user) throws SQLException {
        String req = "UPDATE user " +
                "SET roles = ?, password = ?, cin = ?, nom = ?, prenom = ?, " +
                "num_tel = ?, adress = ?, dob = ?, auth_code = ?, is_verified = ? " +
                "WHERE email = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, user.getRoles()); // Assuming user.getRoles() returns a string representing roles
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCin());
            ps.setString(4, user.getNom());
            ps.setString(5, user.getPrenom());
            ps.setString(6, user.getNum_tel());
            ps.setString(7, user.getAdress());
            ps.setDate(8, user.getDob());
            ps.setString(9, null); // null until the verification system is implemented
            ps.setInt(10, 0); // 0 until the verification system is implemented
            ps.setString(11, user.getEmail()); // Specify the email for the WHERE clause

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful");
            } else {
                System.out.println("User not found or update failed");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating user: " + e.getMessage());
        }

    }

    @Override
    public ResultSet selectAll() throws SQLException {
        ResultSet rs = null;
        String query = "SELECT * FROM user"; // Assuming your table name is 'user'

        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error occurred while selecting all users: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }

        return rs;
    }

    public User Login(String email, String password)
    {
        User user = new User();

        String req = "SELECT * FROM user WHERE email = ? AND  password = ?";
        try(PreparedStatement ps = cnx.prepareStatement(req)){;
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                user.setEmail(resultSet.getString("email"));
                user.setRoles(resultSet.getString("roles"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setPassword(resultSet.getString("password"));
                user.setDob(resultSet.getDate("dob"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setCin(resultSet.getString("cin"));
                user.setAdress(resultSet.getString("adress"));
                user.setIs_verified(resultSet.getInt("is_verified"));
            }
            else {
                user = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        return user ;

    }
    public User searchByEmail(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setRoles(resultSet.getString("roles"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setPassword(resultSet.getString("password"));
                user.setDob(resultSet.getDate("dob"));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setCin(resultSet.getString("cin"));
                user.setAdress(resultSet.getString("adress"));
                user.setIs_verified(resultSet.getInt("is_verified"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while searching for user by email: " + e.getMessage());
            throw e;
        }

        return user;
    }

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking email existence: " + e.getMessage());
        }
        return false;
    }
    public boolean cinExists(String cin) {
        String query = "SELECT COUNT(*) FROM user WHERE cin = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, cin);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking cin existence: " + e.getMessage());
        }
        return false;
    }


}
