package com.alphadev.artemisjvfx.services;

import com.alphadev.artemisjvfx.models.Remboursement;
import com.alphadev.artemisjvfx.models.pret;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRemborsement implements CRUD<Remboursement>{
    private Connection cnx;
    Statement ste;

    public ServiceRemborsement() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void add(Remboursement remboursement) {
        try {
            String req = "INSERT INTO `remboursement` (`valeur`, `duree`,`valeur_tranche`,`etat`,`pret_id`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, remboursement.getValeur());
            ps.setInt(2, remboursement.getDuree());
            ps.setDouble(3, remboursement.getValeur_tranche());

            ps.setString(4, remboursement.getEtat());

            ps.setInt(5, remboursement.getPret());


            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Remboursement remboursement) throws SQLException {
        try {
            String req = "DELETE FROM `remboursement` WHERE `id` =" +remboursement.getId();
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.executeUpdate(req);
            System.out.println("Remboursement supprimée ID!"+remboursement.getId());
            System.out.println("Remboursement supprimée avec succees !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }




    }

    @Override
    public void update(Remboursement remboursement) throws SQLException {
        try {
            String req = "UPDATE `remboursement` SET `valeur` = '" + remboursement.getValeur() + "',`duree`='" + remboursement.getDuree() + "',`valeur_tranche`='" + remboursement.getValeur_tranche()+"',`etat`='" + remboursement.getEtat()+"',`pret_id`='" + remboursement.getPret()+"' WHERE `remboursement`.`id` = " + remboursement.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Remboursement  updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ResultSet selectAll() throws SQLException {
        return null;
    }

    @Override
    public List<Remboursement> realAll() {
        List<Remboursement> remb = new ArrayList<Remboursement>();
        try {
            ste = cnx.createStatement();
            String req = "SELECT * FROM `remboursement`";

            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
               pret pret = new pret(result.getInt("id"),result.getDouble("valeur"), result.getString("motif"), result.getInt("salaire"),result.getInt("garantie"), result.getString("valeur_garantie"),result.getInt("user_id"));
                Remboursement resultRemb = new Remboursement(result.getInt("id"), result.getDouble("valeur"), result.getInt("duree"), result.getDouble("valeur_tranche"), result.getString("etat"),pret);
                remb.add(resultRemb);
            }
            System.out.println(remb);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return remb;
    }
    public List<Remboursement> realAll1() {
        List<Remboursement> remb = new ArrayList<>();
        try (
                Statement ste = cnx.createStatement();
                ResultSet result = ste.executeQuery("SELECT remboursement.*, pret.* FROM remboursement LEFT JOIN pret ON pret.id = remboursement.pret_id")
        ) {
            while (result.next()) {
                pret pret = new pret(result.getInt("id"), result.getDouble("valeur"), result.getString("motif"), result.getInt("salaire"), result.getInt("garantie"), result.getString("valeur_garantie"), result.getInt("user_id"));
                Remboursement remboursement = new Remboursement(result.getInt("id"), result.getDouble("valeur"), result.getInt("duree"), result.getDouble("valeur_tranche"), result.getString("etat"), pret);

                // Ajout à la liste
                remb.add(remboursement);

                // Affichage des données de l'objet
               // System.out.println("ID : " + remboursement.getId());
               // System.out.println("Valeur : " + remboursement.getValeur());
                //ystem.out.println("Durée : " + remboursement.getDuree());
               // System.out.println("Etat : " + remboursement.getEtat());

                // Affichage des données du prêt associé (optionnel)
                //if (pret != null) {
                //    System.out.println("ID du prêt : " + pret.getId());
                //    System.out.println("Valeur du prêt : " + pret.getValeur());
               // }
//
               // System.out.println("-----------------------");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur SQL : " + ex.getMessage());
        }
        return remb;
    }


}
