package com.alphadev.artemisjvfx.models;

import java.util.Date;

public class User {
  //attributes
    private String email;
    private String roles;
    private String password;
    private String cin;
    private String nom;
    private String prenom;
    private String num_tel;
    private String adress;
    private java.sql.Date dob;
    private User auth_code = null;
    private int is_verified;
  /*- -*/

  //constructors
    public User() {
    }

    public User(String email, String roles, String password, String cin, String nom, String prenom, String num_tel, String adress, java.sql.Date dob,User auth_code, int
            is_verified) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.adress = adress;
        this.dob = dob;
        this.auth_code = auth_code;
        this.is_verified = is_verified;
    }
/*- -*/

  // getters & setters :
    //email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //roles
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    //password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //cin
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
    //nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    //prenom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    //num_tel
    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }
    //adress
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    //dob
    public java.sql.Date getDob() {
        return dob;
    }

    public void setDob(java.sql.Date dob) {
        this.dob = dob;
    }
    //is_verified
    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }
    //  auth_code
    public User getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(User auth_code) {
        this.auth_code = auth_code;
    }
  /*- -*/
}

