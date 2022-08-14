package mlinzifact;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import static mlinzifact.dbo.Con;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Print {

    public static void Imprimer(String requete, String cheminJasper) {
//        System.err.println(requete);
        JasperPrint a = null;
        try {
            JasperDesign g = JRXmlLoader.load(fichiers.CreadFolder(null) + cheminJasper + ".jrxml");
            JRDesignQuery gn = new JRDesignQuery();
            gn.setText(requete);
            g.setQuery(gn);
            JasperReport f1 = JasperCompileManager.compileReport(g);
            a = JasperFillManager.fillReport(f1, null, Con());
            JasperViewer.viewReport(a, false);
//                JasperPrintManager.printReport(a, true);
//                  JasperViewer view = new JasperViewer(a, false);
            JasperPrintManager.printReport(a, true);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean Imprimer(String requete, String fichier, Map parametres, Boolean Ouvrir_fenetre) {
        System.out.println(requete);
        JasperPrint a = null;
        try {
            System.out.println(requete);
            if (Querry.getCountBy(requete) > 0) {
                JasperDesign jaspertDisign = JRXmlLoader.load(fichiers.CreadFolder(null) + fichier + ".jrxml");
                JRDesignQuery gn = new JRDesignQuery();
                gn.setText(requete);
                jaspertDisign.setQuery(gn);
                JasperReport Fichier = JasperCompileManager.compileReport(jaspertDisign);
                a = JasperFillManager.fillReport(Fichier, parametres, Con());
                JasperPrintManager.printReport(a, Ouvrir_fenetre);
                JasperViewer view = new JasperViewer(a, false);
                view.setTitle("Fiche de rapport");
                view.setResizable(true);
                InputStream stram = getClass().getResourceAsStream(manifest.APP_ICON);
                ImageIcon icon = new ImageIcon(ImageIO.read(stram));
                view.setIconImage(icon.getImage());
                view.show();
                return true;
            } else {
                cls.c.setAlert("Aucune donnée n'as été trouvée !", Alert.AlertType.ERROR);
                return false;
            }
        } catch (JRException ex) {
            fichiers.error("Erreur d'impression : >>" + requete + " --> " + ex.getMessage());
            cls.c.setAlert("Aucune donnée n'as été trouvée !" + ex, Alert.AlertType.ERROR);
        } catch (IOException ex) {
            fichiers.error("Erreur d'impression : >>" + requete + " --> " + ex.getMessage());
        }
        return false;
    }

    public static boolean Facture(String requete, Map parametres, String cheminJasper) {
        JasperPrint a = null;
        if (Querry.getCountBy(requete) > 0) {
            try {
                JasperDesign g = JRXmlLoader.load(cheminJasper);
                JRDesignQuery gn = new JRDesignQuery();
                gn.setText(requete);
                g.setQuery(gn);
                JasperReport Fichier = JasperCompileManager.compileReport(g);
                a = JasperFillManager.fillReport(Fichier, parametres, Con());
                JasperPrintManager.printReport(a, false);
                return true;
            } catch (JRException ex) {
                fichiers.error("Une erreur est survenue lor de l'impression de la facture \\" + ex.getMessage());
            }
        } else {
            cls.c.setAlert("Aucune valeur trouvée pour cette facture", Alert.AlertType.ERROR);
        }

        return false;
    }
}
