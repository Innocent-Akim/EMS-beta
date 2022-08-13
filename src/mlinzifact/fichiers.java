/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vulembere
 */
public class fichiers {

    public static String CreadFolder(String url) {
        String path = null;
        String Localchemain = null;
        if (url != null) {
            Localchemain = url;
        } else {
            Localchemain = System.getProperty("user.home") + "\\Mlinzi-Fact";
        }
        File files = new File(Localchemain);
        if (!files.exists()) {
            if (files.mkdirs()) {
                path = Localchemain;
                System.err.println("### Creaded");
            }
        } else {
            path = Localchemain + "\\";
        }
        return path;
    }

    public static boolean ExitFolder(String url) {
        File files = new File(url);
        return files.exists();
    }
    public static final String ROOT = CreadFolder(null);

    /**
     * Ce procédure écrit dans le fichier les lignes de la liste
     *
     * @param nomFichier : nom du fichier dans lequel écrire
     */
    public static List<String> lire(String Nomfichier) {
        BufferedReader fluxEntree = null;
        String ligneLue;
        List<String> lignes = new ArrayList<String>();
        try {
            String lien = ROOT + "\\" + Nomfichier + ".config";
            if (new File(lien).exists()) {
                System.out.println("Exist");
            } else {
                new File(lien).createNewFile();
//                BufferedInputStream in = new BufferedInputStream(new URL(lien).openStream());
                System.out.println("Not exist");
            }
            fluxEntree = new BufferedReader(new FileReader(lien));
            ligneLue = fluxEntree.readLine();
            while (ligneLue != null) {
                lignes.add(ligneLue);
                ligneLue = fluxEntree.readLine();
            }
        } catch (IOException exc) {
            System.err.println(exc);
            exc.printStackTrace();
        }
        try {
            fluxEntree.close();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return lignes;
    }

    public static String getContaint(String lien) {
        BufferedReader fluxEntree = null;
        String ligneLue = null;
        try {

            fluxEntree = new BufferedReader(new FileReader(lien));
            ligneLue = fluxEntree.readLine();

        } catch (IOException exc) {
            System.err.println(exc);
            exc.printStackTrace();
        }
        try {
            fluxEntree.close();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return ligneLue;
    }

    public static String lireError() {
        BufferedReader fluxEntree = null;
        String ligneLue;
        String lignes = "";
        try {
            String lien = ROOT + "\\dbsysteme_error_log.log";
            if (new File(lien).exists()) {
                System.out.println("Exist");
            } else {
                new File(lien).createNewFile();
//                BufferedInputStream in = new BufferedInputStream(new URL(lien).openStream());
                System.out.println("Not exist");
            }
            fluxEntree = new BufferedReader(new FileReader(lien));
            ligneLue = fluxEntree.readLine();
            while (ligneLue != null) {
                lignes += ligneLue.toString() + "\n";
                ligneLue = fluxEntree.readLine();
            }
        } catch (IOException exc) {
            System.err.println(exc);
            exc.printStackTrace();
        }
        try {
            fluxEntree.close();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return lignes;
    }

    /**
     * Ce procédure écrit dans le fichier les lignes de la liste
     *
     * @param nomFichier : nom du fichier dans lequel écrire
     * @param lignes : liste des lignes à écrire
     */
    public static void ecrireFichier(String nomFichier, List<String> lignes) {
        Writer fluxSortie = null;
        try {
            fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(
                    fichiers.ROOT + nomFichier + ".config")));
            for (int i = 0; i < lignes.size() - 1; i++) {
                byte[] ptext = lignes.get(i).getBytes(UTF_8);
                String value = new String(ptext, UTF_8);
                if (i == (lignes.size() - 1)) {
                    fluxSortie.write(value);
                } else {
                    fluxSortie.write(value + "\n");
                }
            }
            fluxSortie.write(lignes.get(lignes.size() - 1));

        } catch (IOException exc) {
            exc.printStackTrace();
        }
        try {
            fluxSortie.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isError = false;
    public static String isMessage = null;
    public static boolean runFactError;

    public static void error(String lignes) {
        isError = true;
        isMessage = lignes;
        String error = lireError();
        String date = LocalDateTime.now().toString();
        try {
            Writer fluxSortie = null;

            fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(
                    fichiers.ROOT + "dbsysteme_error_log.log")));
            fluxSortie.write(error + "__ Date : " + date + " >>MACHINE : " + Inet.getHosName() + " ERROR [[ " + lignes + " ]]\n");
            fluxSortie.write(-1);
            try {
                fluxSortie.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toolkit.getDefaultToolkit().beep();
        } catch (IOException ex) {
            Logger.getLogger(fichiers.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("Erreur --> " + lignes);
    }

    public static void errorClear() {
        String date = LocalDateTime.now().toString();
        try {
            Writer fluxSortie = null;

            fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter(
                    fichiers.ROOT + "dbsysteme_error_log.log")));
            fluxSortie.write("");
            try {
                fluxSortie.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(fichiers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
