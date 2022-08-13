/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import org.json.JSONArray;
import org.json.JSONObject;
import resultsettojson.Tables;
import javafx.concurrent.Service;

/**
 *
 * @author Vulembere
 */
public class synchro {

    public static void start() {
        final Service<Void> SetService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (Inet.isPing("www.google.com")) {
                            synchro.synchDevise();
                            synchro.synchProduit();
                            synchro.synchloadcomptes();
                            synchro.synchUsers();
                            synchro.synchEntreprise();
                            synchro.synchroOperations();
                        }
                        return null;
                    }
                };
            }

        };
        SetService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                    start();
                    break;
                case CANCELLED:
                    start();
                    break;
                case SUCCEEDED:
                    System.out.println("SUCCED synchronise");
                    break;
            }
        }
        );
        SetService.start();

    }

    public static void synchUsers() {
        String table = "users";
        JSONObject object = HttpRequest.getHttpReqest("/loadusers", "GET", null, null);
        JSONArray data = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ob = data.getJSONObject(i);
            if (Querry.exist(table, ob.getString("id"))) {
                Querry.execute("UPDATE `users` SET `psedo`=?,`password_`=?,`etat`=? where id=?",
                        ob.getString("psedo"), ob.getString("password"), ob.getString("etat"), ob.getString("id"));
            } else {
                Querry.execute("INSERT INTO `users` SET `id`=?,`psedo`=?,`password_`=?,`etat`=?",
                        ob.getString("id"), ob.getString("psedo"), ob.getString("password"), ob.getString("etat"));
            }
        }
    }

    public static void synchProduit() {
        String table = "produit";
        JSONObject object = HttpRequest.getHttpReqest("/loadproduit", "GET", null, null);
        JSONArray data = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ob = data.getJSONObject(i);
            if (Querry.exist(table, ob.getString("id"))) {
                Querry.execute("UPDATE `produit` SET `nom`=?,`prix_min`=?,`prix_max`=?,`qte`=?,`barcode`=?,`description`=?,id_entrep=? where id=?",
                        ob.getString("produit"), ob.getString("prix_min"), ob.getString("prix"), ob.getString("qte"), ob.getString("barcode"), ob.getString("observation"), ob.getString("id_entrep"), ob.getString("id"));
            } else {
                Querry.execute("INSERT INTO `produit` SET `id`=?,`nom`=?,`prix_min`=?,`prix_max`=?,`qte`=?,`date_approv`=?,barcode=?,id_entrep=?,description=?",
                        ob.getString("id"), ob.getString("produit"), ob.getString("prix_min"), ob.getString("prix"), ob.getString("qte"), ob.getString("date_create"), ob.getString("barcode"), ob.getString("observation"), ob.getString("id_entrep"));
            }
        }
    }

    public static void synchloadcomptes() {
        String table = "clients";
        JSONObject object = HttpRequest.getHttpReqest("/loadcomptes", "GET", null, null);
        JSONArray data = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ob = data.getJSONObject(i);
            if (Querry.exist(table, ob.getString("id"))) {
                Querry.execute("UPDATE `clients` SET `nom`=? where id=?",
                        ob.getString("compte"), ob.getString("id"));
            } else {
                Querry.execute("INSERT INTO `clients` SET `id`=?,`nom`=?",
                        ob.getString("id"), ob.getString("compte"));
            }
        }
    }

    public static void synchDevise() {
        String table = "devises";
        JSONObject object = HttpRequest.getHttpReqest("/loaddevise", "GET", null, null);
        JSONArray data = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ob = data.getJSONObject(i);
            if (Querry.exist(table, ob.getString("id"))) {
                Querry.execute("UPDATE `devises` SET designation=?,`description`=?,`taux`=?,`etat`=? where id=?",
                        ob.getString("designation"), ob.getString("description"), ob.getString("taux"), ob.getString("etat"), ob.getString("id"));
            } else {
                Querry.execute("INSERT INTO `devises` SET `id`=?,`designation`=?,`description`=?,`taux`=?,`etat`=?",
                        ob.getString("id"), ob.getString("designation"), ob.getString("description"), ob.getString("taux"), ob.getString("etat"));
            }
        }
    }

    public static void synchEntreprise() {
        String table = "entreprise";
        JSONObject object = HttpRequest.getHttpReqest("/loadentreprise", "GET", null, null);
        JSONArray data = object.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject ob = data.getJSONObject(i);
            if (Querry.exist(table, ob.getString("id"))) {
                Querry.execute("UPDATE `entreprise` SET `name`=?,`phone`=?,`email`=?,`adresse`=?,`logo`=? where `id`=?",
                        ob.getString("name"), ob.getString("phone"), ob.getString("email"), ob.getString("adresse"), ob.getString("logo"), ob.getString("id"));
            } else {
                Querry.execute("INSERT INTO `entreprise` SET id=?,`name`=?,`phone`=?,`email`=?,`adresse`=?,`logo`=?",
                        ob.getString("id"), ob.getString("name"), ob.getString("phone"), ob.getString("email"), ob.getString("adresse"), ob.getString("logo"));
            }
        }
    }

    public static void synchroOperations() {
        JSONArray array = Tables.get(dbo.Con(), "SELECT * FROM `operation` Where synchro=0");
        for (int i = 0; i < array.length(); i++) {
            JSONObject ob = array.getJSONObject(i);
            JSONObject object = HttpRequest.getHttpReqest("/operations-setOperation", "POST", ob, null);
            if (HttpRequest.statut == 200) {
                Querry.execute("update operation set synchro=1 Where id=?", ob.getString("id"));
            }
        }

    }

    public static void synchroOperations_detailles() {
        JSONArray array = Tables.get(dbo.Con(), "SELECT * FROM `operation_detaille` Where synchro=0");
        for (int i = 0; i < array.length(); i++) {
            JSONObject ob = array.getJSONObject(i);
            JSONObject object = HttpRequest.getHttpReqest("/operations-detailles", "POST", ob, null);
            if (HttpRequest.statut == 200) {
                Querry.execute("update operation_detaille set synchro=1 Where id=?", ob.getString("id"));
            }
        }

    }
}
