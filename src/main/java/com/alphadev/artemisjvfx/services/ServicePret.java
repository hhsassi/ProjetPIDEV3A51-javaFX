package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.pret;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePret implements CRUD<pret> {

    private Connection cnx;
    Statement ste;

    public ServicePret() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void add(pret pret) {
        String req = "INSERT INTO pret (valeur, motif, salaire, garantie, valeur_garantie , user_id) " +
                "VALUES ( ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {

            ps.setDouble(1, pret.getValeur());
            ps.setString(2, pret.getMotif()); // Assuming user.getRoles() returns a string representing roles
            ps.setInt(3, pret.getSalaire());
            ps.setInt(4, pret.getGarantie());
            ps.setString(5, pret.getValeur_garantie());
            ps.setInt(6, pret.getUser_id());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pret added successfully");
            } else {
                System.out.println("Failed to add Pret");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while adding user: " + e.getMessage());
        }

    }

    @Override
    public void delete(pret pret) {
        try {
            String req = "DELETE FROM `pret` WHERE `id` =" + pret.getId();
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.executeUpdate(req);
            System.out.println("Pret supprimée avec succees !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void update(pret pret)  {
        try {
            String req = "UPDATE `pret` SET `valeur` = '" + pret.getValeur() + "',`motif`='" + pret.getMotif() + "',`salaire`='" + pret.getSalaire()+"',`garantie`='" + pret.getGarantie()+"',`valeur_garantie`='" + pret.getValeur_garantie()+"',`user_id`='" + pret.getUser_id()+"' WHERE `pret`.`id` = " + pret.getId();
            Statement st = cnx.createStatement();
            ((Statement) st).executeUpdate(req);
            System.out.println("Pret updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ResultSet selectAll() throws SQLException {
        return null;
    }

    @Override
    public List<pret> realAll() {
        List<pret> prets = new ArrayList<pret>();
        try {
            ste = cnx.createStatement();
            String req = "SELECT * FROM `pret`";
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                pret resultPret= new pret(result.getInt("id"),result.getDouble("valeur"), result.getString("motif"), result.getInt("salaire"),result.getInt("garantie"), result.getString("valeur_garantie"),result.getInt("user_id") );
                prets.add(resultPret);
            }
            System.out.println(prets);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return prets;
    }
}