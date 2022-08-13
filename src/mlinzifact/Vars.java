/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Vulembere
 */

public class Vars {

    public static String USER_ID;
    public static String USER_USERNAME;
    public static String USER_ID_ENTREP;

    public static String COMPTE_ID;
    public static String COMPTE_ID_SOUS;
    public static String COMPTE_DESIGNATIONS;
    public static String COMPTE_NUMERO;

    public static String OPERATION_TYPE;
    public static String OPERATION_ID;

    public static String DEVISE_ID;
    public static String DEVISE_DEFAULT;
    public static Float DEVISE_TAUX;
    public static String DEVISE_DESCRIPTION;

    public static ArrayList<String> LISTE_COMPTE_1;
    public static ArrayList<String> LISTE_COMPTE_2;
    public static ArrayList<String> LISTE_BENEFICAIRE;

    public static String PRODUIT_ID;
    public static String PRODUIT_CATEGORIE_ID;
    public static String PRODUIT_NAME;
    public static String PRODUIT_NUMERO;
    


    public static int create_name = 1;

    public static void loadClass(ComboBox box) {
        box.getItems().add("Actif");
        box.getItems().add("Passif");
        box.getItems().add("Avoir");
        box.getItems().add("Produit");
        box.getItems().add("Charge");
        box.getItems().add("Autre");
    }

    public static void loadGenre(ComboBox box) {
        box.getItems().add("Homme");
        box.getItems().add("Femme");
        box.getItems().add("Entreprise/Organisation");
        box.getItems().add("Autre");
    }

    public static void loadTypeProduit(ComboBox box) {
        box.getItems().add("Produit");
        box.getItems().add("Service");
    }

    public static ArrayList<String> loadListes(String element) {
        return Querry.getValueList("SELECT designation FROM `compte_sous` WHERE designation<>? and id_entrep=?", element, Vars.USER_ID_ENTREP);
    }
    public void SelectDataFor(Button... bt) {
        try {
            for (Button tr : bt) {
                tr.setStyle("-fx-background-color: #fff;-fx-text-fill: #000000;");
                tr.setFont(Font.font("System", FontPosture.REGULAR, 13));
            }
            bt[0].setStyle("-fx-background-color: #2C5E1A;-fx-text-fill: #fff;");
            bt[0].setFont(Font.font("Century Gothic", FontWeight.BOLD, 13));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void defaultDevise() {
        try {
            ResultSet rs = Querry.getRs("select * from devises where etat=1 ");
            while (rs.next()) {
                DEVISE_ID = rs.getString("id");
                DEVISE_DEFAULT = rs.getString("designation");
                DEVISE_TAUX = rs.getFloat("taux");
                DEVISE_DESCRIPTION = rs.getString("description");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Vars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void loadDevise(ComboBox box) {
        Querry.loadCombo(box, "SELECT designation FROM `devises`");
    }

    public static void loadCategorie(ComboBox box) {
        Querry.loadCombo(box, "SELECT designation FROM `produit_categorie` where id_entrep=?", Vars.USER_ID_ENTREP);
    }
    
    public static void loadEntrep(ComboBox box) {
        Querry.loadCombo(box, "SELECT name FROM `entreprise`");
    }

    public static void loadComptes(ComboBox box) {
        Querry.loadCombo(box, "SELECT designation from compte_sous where etat=1 and id_entrep='" + Vars.USER_ID_ENTREP + "'");
    }

    public static void loadFournisseur(ComboBox box) {
        Querry.loadCombo(box, "SELECT designation from compte_sous where  etat=1 and id_entrep='" + Vars.USER_ID_ENTREP + "';");
    }

    public static String getCompteNumber(String numero) {
        String numeroCompte = Querry.getValue("select id from compte_sous where numero=? and id_entrep=?", numero, Vars.USER_ID_ENTREP);
        return numeroCompte;
    }
}
