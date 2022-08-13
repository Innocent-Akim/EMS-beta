/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import GUIController.FACTURATIONController;
import static GUIController.FACTURATIONController.txtMontant_;
import GUIController.LoadArticleVenteController;
import GUIController.loadProduitFactureController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import mlinzifact.Querry;
import mlinzifact.Vars;
import mlinzifact.manifest;

/**
 *
 * @author Vulembere
 */
public class Facturation {

    public static ArrayList<String> LIST_NUMERO = new ArrayList();
    public static ArrayList<String> LIST_PRODUIT = new ArrayList();
    public static ArrayList<String> LIST_PRIX = new ArrayList();
    public static ArrayList<String> LIST_QUANTITE = new ArrayList();
    public static ArrayList<String> LIST_BARCODE = new ArrayList();
    public static ArrayList<String> LIST_ID = new ArrayList();

    public static ArrayList<String> LIST_VENTE_ID = new ArrayList();
    public static ArrayList<String> LIST_VENTE_PRODUIT = new ArrayList();
    public static ArrayList<Float> LIST_VENTE_QTE = new ArrayList();
    public static ArrayList<Float> LIST_VENTE_PU = new ArrayList();
    public static ArrayList<Float> LIST_VENTE_PT = new ArrayList();

    public static ArrayList<String> LIST_INVESTISSEMENT = new ArrayList();

    public static void init(String id_entrep) {
        System.out.println("||" + id_entrep);
        LIST_NUMERO.clear();
        LIST_PRIX.clear();
        LIST_PRODUIT.clear();
        LIST_ID.clear();
        LIST_QUANTITE.clear();
        LIST_BARCODE.clear();
//        removeList(LIST_BARCODE, LIST_ID, LIST_PRODUIT, LIST_PRIX, LIST_NUMERO, LIST_QUANTITE);
        try {
            removeAll();
            ResultSet rs = Querry.getRs("SELECT * FROM `produit` where id_entrep=?", id_entrep);
            int x = 1;
            while (rs.next()) {
                LIST_NUMERO.add(x + "");
                LIST_PRODUIT.add(rs.getString("nom").toUpperCase());
                LIST_PRIX.add(rs.getString("prix_max"));
                LIST_QUANTITE.add(rs.getString("qte"));
                LIST_ID.add(rs.getString("id"));
                LIST_BARCODE.add(rs.getString("barcode"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Facturation.class.getName()).log(Level.SEVERE, null, ex);
        }
        LIST_INVESTISSEMENT = Querry.getArray("SELECT nom FROM `clients`");

    }

    public static void removeList(ArrayList... node) {
        for (ArrayList nodeList : node) {
            nodeList.clear();
        }
    }

    public void loadProduis(ListView liste, String txt) {

        liste.getItems().clear();

        for (int x = 0; x < LIST_ID.size(); x++) {
            if (txt != null && !txt.equals("")) {
                if (LIST_PRODUIT.get(x).contains(txt.toUpperCase()) || LIST_BARCODE.get(x).contains(txt)) {
                    charger(liste, x);
                }
            } else {
                charger(liste, x);
            }
        }

    }

    void charger(ListView liste, int x) {
//        LoadArticleVenteController.designation = LIST_PRODUIT.get(x);
        liste.getItems().addAll(LIST_PRODUIT.get(x).trim() + "||" + LIST_BARCODE.get(x).trim());
    }

    public static void updateFact(int index, float prix, float qte_) {
        LIST_VENTE_PU.set(index, prix);
        LIST_VENTE_QTE.set(index, qte_);
        fact.loadDetailleFact(FACTURATIONController.liste_detaille_);
    }

    public static void removeAll() {
        LIST_VENTE_PU.clear();
        LIST_VENTE_QTE.clear();
        LIST_VENTE_PT.clear();
        LIST_VENTE_PRODUIT.clear();
        LIST_VENTE_ID.clear();

    }

    public static void deleteFact(int index) {
        LIST_VENTE_PU.remove(index);
        LIST_VENTE_QTE.remove(index);
        LIST_VENTE_PT.remove(index);
        LIST_VENTE_PRODUIT.remove(index);
        LIST_VENTE_ID.remove(index);
        fact.loadDetailleFact(FACTURATIONController.liste_detaille_);
    }

    public static void addFact(String produit, String id, float prix, float qte_) {
        if (LIST_VENTE_PRODUIT.contains(produit.toUpperCase())) {

            int index = LIST_VENTE_PRODUIT.indexOf(produit);
            float pu = LIST_VENTE_PU.get(index);

            float qte = LIST_VENTE_QTE.get(index) + qte_;

            System.out.println(pu + "###" + prix);

            if (pu == prix) {
                LIST_VENTE_QTE.set(index, qte);
            } else {
                addDet(produit, id, qte_, prix);
            }
        } else {
            addDet(produit, id, qte_, prix);
        }
        fact.loadDetailleFact(FACTURATIONController.liste_detaille_);
    }

    static void addDet(String produit, String id, float qte, float prix) {
        LIST_VENTE_ID.add(id);
        LIST_VENTE_PRODUIT.add(produit);
        LIST_VENTE_PU.add(prix);
        LIST_VENTE_QTE.add(qte);
        LIST_VENTE_PT.add(qte * prix);
    }

    public void loadDetailleFact(ListView listView) {
        listView.getItems().clear();
        float sommes = 0;
        for (int i = 0; i < LIST_VENTE_ID.size(); i++) {
            try {
                System.out.println(LIST_VENTE_ID.get(i));
                loadProduitFactureController.designation = LIST_VENTE_PRODUIT.get(i);
                loadProduitFactureController.id = LIST_VENTE_ID.get(i);
                loadProduitFactureController.numero = (i + 1) + "";
                loadProduitFactureController.qte = LIST_VENTE_QTE.get(i) + "";
                loadProduitFactureController.pu = LIST_VENTE_PU.get(i) + "";
                loadProduitFactureController.pt = String.valueOf((LIST_VENTE_PU.get(i) * LIST_VENTE_QTE.get(i)));
                sommes += LIST_VENTE_PU.get(i) * LIST_VENTE_QTE.get(i);
                listView.getItems().add(FXMLLoader.load(getClass().getResource(manifest.LOADER_PRODUIT_FACTURE)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        txtMontant_.setText(" " + sommes + "");
    }

    public static String getMotif() {
        String txt = "";
        for (int i = 0; i < LIST_VENTE_PRODUIT.size(); i++) {
            txt += LIST_VENTE_PRODUIT.get(i) + " (" + LIST_VENTE_QTE.get(i) + " X " + LIST_VENTE_PU.get(i) + ") ;";
        }
        return txt;
    }

    public static void saveDetailles(String idOperation) {

        for (int i = 0; i < LIST_VENTE_PRODUIT.size(); i++) {
        System.out.println("OPERATION------------------------------> "+LIST_VENTE_ID.get(i));
         boolean bool=   Querry.execute("INSERT INTO `operation_detaille` SET `id`=?,`id_operation`=?,`id_produit`=?,`qte`=?,`prix`=? ,`id_entrep`=?,`barcode`=?",
                    Querry.getId(), idOperation, LIST_VENTE_ID.get(i), LIST_VENTE_QTE.get(i).toString(), LIST_VENTE_PU.get(i).toString(), Vars.USER_ID_ENTREP, LIST_BARCODE.get(i));

        }
    }

    public static void saveID(String entreprise) {
        String code = Querry.getLastId("enteteFacture");
        Querry.execute("INSERT INTO enteteFacture VALUES(?,?) ", code, entreprise);
    }
    public static Facturation fact = new Facturation();
}
