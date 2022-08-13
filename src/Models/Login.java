/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import mlinzifact.Querry;
import mlinzifact.Vars;

/**
 *
 * @author Vulembere
 */
public class Login {

    private String nom, password;

    public Login(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    public boolean log() {
        try {
            ResultSet rs = Querry.getRs("select * from users where psedo=? and password_=md5(?)", this.nom, this.password);
            while (rs.next()) {
                Vars.USER_ID = rs.getString("id");
                Vars.USER_USERNAME = rs.getString("psedo");
                Vars.USER_ID_ENTREP = rs.getString("id_entrep");
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
