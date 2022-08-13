/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.sql.*;
import static mlinzifact.Preference.pref;

/**
 *
 * @author wills
 */
public class dbo {

    public static Connection con;
    public static PreparedStatement pst;
    public static ResultSet rs;
    public static String message = "Message  d'erreur vide !";
    public static int code = 0;
    public static String User, pswd, serveur, Bd;

    public static void init() {
        User = pref.getUser();
        pswd = pref.getPassword();
        serveur = pref.getHost();
        Bd = pref.getBd();
    }

    public static Connection Con() {
        try {
            if (con == null) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + serveur + ":3306/" + Bd, User, pswd);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
            con = null;
        }
        return con;
    }

    public static String getMessage() {
        return message;
    }

}
