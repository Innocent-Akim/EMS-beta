package mlinzifact;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.prefs.Preferences;

/**
 *
 * @author SMART PC
 */
public class Preference {

    public Preferences prefs = Preferences.userNodeForPackage(Preference.class);

    public String getId() {
        return this.prefs.get("id", "");
    }

    public void setId(String id) {
        this.prefs.put("id", id);
    }

    public void setToken(String token) {
        this.prefs.put("token", token);
    }

    public String getToken() {
        return this.prefs.get("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NjAzODQ2MTYsImV4cCI6MTY2MDk4OTQxNiwic3ViIjoiaHR0cHM6XC9cL3d3dy5tbGluemktYXBwLmNvbSIsImlkIjoiMTA5MjIwMDA5OTEwMDIiLCJwaG9uZSI6bnVsbH0.3YvhwGgjYqAMLvgulsXNRmZbM1_hbqumzBVO4MpSCDt-OJnOhjZ-p9F_UoG61dZFsmKezFTT7eJ30_KCqU5tSQ");
    }

    public String getHost() {
        return this.prefs.get("serv", "localhost");
    }

    public void setHost(String value) {
        this.prefs.put("serv", value);
    }

    public String getUser() {
        return this.prefs.get("user", "root");
    }

    public void setUser(String user) {
        this.prefs.put("user", user);
    }

    public String getBd() {
        return this.prefs.get("bd", "mysql");
    }

    public void setBd(String value) {
        this.prefs.put("bd", value);
    }

    public String getPassword() {
        return this.prefs.get("pass", "root");
    }

    public void setPassword(String value) {
        this.prefs.put("pass", value);
    }
    public static Preference pref = new Preference();
}
