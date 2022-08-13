/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import GUIControllers.loadProduitController;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import mlinzifact.Querry;
import mlinzifact.Vars;
import mlinzifact.manifest;

/**
 * FXML Controller class
 *
 * @author ISDR
 */
public class HistoriqueController implements Initializable {

    @FXML
    private TextField txtRecherche1;
    @FXML
    private Button btnReload1;
    @FXML
    private JFXListView<?> loadList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        txtRecherche1.setOnKeyPressed((action) -> {
            initDatasearch(txtRecherche1.getText());
        });
    }

    @FXML
    private void Rechercher(KeyEvent event) {
    }

    @FXML
    private void reload(ActionEvent event) {
    }

    void initData() {
        try {
            loadList.getItems().clear();
            ResultSet rs = Querry.getRs("SELECT * FROM `produit` where id_entrep=? limit 150", Vars.USER_ID_ENTREP);
            while (rs.next()) {
                loadProduitController.codeString = rs.getString("id");
                loadProduitController.designationString = rs.getString("nom").toUpperCase();
                loadProduitController.puString = rs.getString("prix_max");
                loadProduitController.qteString = rs.getString("qte");
                loadProduitController.codeBarString = rs.getString("barcode");
                loadProduitController.dateString = rs.getString("date_create");
                loadList.getItems().add(FXMLLoader.load(getClass().getResource(manifest.LOAD_PRODUIT)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void initDatasearch(String search) {
        try {
            loadList.getItems().clear();
            ResultSet rs = Querry.getRs("SELECT * FROM `produit` where nom like '%" + search + "%' AND id_entrep=? limit 150", Vars.USER_ID_ENTREP);
            while (rs.next()) {
                loadProduitController.codeString = rs.getString("id");
                loadProduitController.designationString = rs.getString("nom").toUpperCase();
                loadProduitController.puString = rs.getString("prix_max");
                loadProduitController.qteString = rs.getString("qte");
                loadProduitController.codeBarString = rs.getString("barcode");
                loadProduitController.dateString = rs.getString("date_create");
                loadList.getItems().add(FXMLLoader.load(getClass().getResource(manifest.LOAD_PRODUIT)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
//       LIST_NUMERO.add(x + "");
//                LIST_PRODUIT.add(rs.getString("nom").toUpperCase());
//                LIST_PRIX.add(rs.getString("prix_max"));
//                LIST_QUANTITE.add(rs.getString("qte"));
//                LIST_ID.add(rs.getString("id"));
//                LIST_BARCODE.add(rs.getString("barcode"));
