package Models;

import java.util.ArrayList;
import javafx.scene.control.Alert;
import mlinzifact.Querry;
import mlinzifact.Vars;
import mlinzifact.cls;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vulembere
 */
public class Operations {

    public static boolean save(String from, String to, String beneficiare, String devise, String deviseConvert, String taux, String montant, String montantConvert, String toute_lettre, String motif, String date) {

        String id_from = Querry.getValue("select id from compte_sous where designation=? and id_entrep=?", from, Vars.USER_ID_ENTREP);
        String id_to = Querry.getValue("select id from compte_sous where designation=? and id_entrep=?", to, Vars.USER_ID_ENTREP);
        String id_beneficiaire = Querry.getValue("select id from compte_sous where designation=? and id_entrep=?", beneficiare, Vars.USER_ID_ENTREP);
        String id_devise = Querry.getValue("select id from devises where designation=? and id_entrep=?", devise, Vars.USER_ID_ENTREP);
        String id_deviseConvert = Querry.getValue("select id from devises where designation=? and id_entrep=?", deviseConvert, Vars.USER_ID_ENTREP);
        String numero = Querry.getIdTable("operation");
        if (id_from != null && id_to != null) {
            if (id_beneficiaire != null) {
                if (Vars.OPERATION_ID == null) {
                    String id = Querry.getId();
                    return Querry.execute("INSERT INTO `operation` SET `id`=?,type_=?,`numero`=?,`compte_from`=?,`compte_to`=?,`id_benefiaire`=?,`id_devise_convert`=?,`taux`=?,`montant`=?,`id_devise`=?,`montant_convert`=?,`montant_toute_lettre`=?,`motif`=?, `user_update`=?,id_entrep=?,date_save=?,ref=?",
                            id, Vars.OPERATION_TYPE.trim(), numero, id_from, id_to, id_beneficiaire, id_deviseConvert, taux, montant, id_devise, montantConvert, toute_lettre, motif, Vars.USER_ID, Vars.USER_ID_ENTREP, date, numero);

                } else {
                    return Querry.execute("UPDATE `operation` SET type_=?,`numero`=?,`compte_from`=?,`compte_to`=?,`id_benefiaire`=?,`id_devise_convert`=?,`taux`=?,`montant`=?,`id_devise`=?,`montant_convert`=?,`montant_toute_lettre`=?,`motif`=?, `user_update`=?,date_save=? where id=?",
                            Vars.OPERATION_TYPE.trim(), numero, id_from, id_to, id_beneficiaire, id_deviseConvert, taux, montant, id_devise, montantConvert, toute_lettre, motif, Vars.USER_ID, date, Vars.OPERATION_ID);
                }
            } else {
                cls.c.setAlert("Le beneficiaire n'existe pas dans le systeme veiller enregistrer en avance.", Alert.AlertType.WARNING);
            }
        } else {
            cls.c.setAlert("L'un des deux compte est invalide. Veiller vous reseigner au pret de la comptabilité", Alert.AlertType.WARNING);
        }

        return false;
    }

    public static boolean saveFact(String id, String Type, String ref, String numero, String id_from, String id_to, String beneficiare, String devise, String deviseConvert, String taux, String montant, String montantConvert, String toute_lettre, String motif, String date, String reste,String etat) {

 

        if (id_from != null && id_to != null) {

            return Querry.execute("INSERT INTO `operation` SET `ref`=?,`id`=?,type_=?,`numero`=?,`compte_from`=?,`compte_to`=?,`id_benefiaire`=?,`id_devise_convert`=?,`taux`=?,`montant`=?,`id_devise`=?,`montant_convert`=?,`montant_toute_lettre`=?,`motif`=?, `user_update`=?,id_entrep=?,date_save=?,reste=?,etat=?",
                    ref, id, Type, numero, id_from, id_to, beneficiare, deviseConvert, taux, montant.trim(), devise, montantConvert, toute_lettre, motif, Vars.USER_ID, Vars.USER_ID_ENTREP, date, reste,etat);
        } else {
            cls.c.setAlert("L'un des deux compte est invalide. Veiller vous reseigner au pret de la comptabilité", Alert.AlertType.WARNING);
        }

        return false;
    }

    public static ArrayList<String> LISTE_COMPTE_RAPPORT = new ArrayList();
    public static ArrayList<Float> LISTE_MONTANT = new ArrayList();

    void setRap(String type, float montant) {
        if (LISTE_COMPTE_RAPPORT.contains(type)) {
            int index = LISTE_COMPTE_RAPPORT.indexOf(type);
            float montant_ = LISTE_MONTANT.get(index);
            LISTE_MONTANT.set(index, (montant + montant_));
        } else {
            LISTE_COMPTE_RAPPORT.add(type);
            LISTE_MONTANT.add(montant);

        }
    }

    public static Operations op = new Operations();
}
