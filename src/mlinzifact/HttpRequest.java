/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import static mlinzifact.Preference.pref;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MC
 */
public class HttpRequest {

    public static String token_ = "";
    public static String message;
    public static String URLBase = "https://ems.databankrdc.com/api"; 

    public static int statut;

    public static int getStatut() {
        return statut;
    }

    public static void setStatut(int statut) {
        HttpRequest.statut = statut;
    }

    public static JSONObject getHttpReqest(String route, String methode, JSONObject json, String token) {
        JSONObject retour_ = null;
        if (token == null) {
            token_ = pref.getToken();
        } else {
            token_ = token;
        }

        try {
            RequestBody body = null;
            if (json != null) {
                body = RequestBody.create(MediaType.parse("application/json"), json.toString());
            }

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request;
            String uri = URLBase + route;
            System.out.println("||"+uri);
            if (methode.equals("GET")) {
                request = new Request.Builder()
                        .url(uri)
                        .header("Authorization", "Bearer " + token_)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(uri)
                        .method(methode, body)
                        .header("Authorization", "Bearer " + token_)
                        .build();
            }
            Response responses = client.newCall(request).execute();
            statut = responses.code();
            String data = responses.body().string();
            
            if (data.equals("Forbidden")) {
                retour_ = null;
            } else {
                try {
                    retour_ = new JSONObject(data);
                } catch (JSONException e) {
                    System.err.println("++" + e);
                }
            }
 
        } catch (IOException ex) {
            message = ex.getMessage();
            fichiers.error("URL ==> "+URLBase + route+" eRROR==>"+ message);
            retour_ = null;
        }
        return retour_;

    }

    public static String getToken_() {
        return token_;
    }

    public static void setToken_(String token_) {
        HttpRequest.token_ = token_;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        HttpRequest.message = message;
    }

    public static boolean isPing(String Ip) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        boolean bool = false;
        try {
            InetAddress inet = InetAddress.getByName(Ip);
            if (inet.isReachable(5000)) {
                bool = true;
            } else {
                bool = false;
                tk.beep();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return bool;
    }
}
