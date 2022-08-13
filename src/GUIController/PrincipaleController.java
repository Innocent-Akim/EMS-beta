/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import mlinzifact.Querry;
import mlinzifact.manifest;

/**
 * FXML Controller class
 *
 * @author ISDR
 */
public class PrincipaleController implements Initializable {

    @FXML
    private JFXButton btn_historique;
    @FXML
    private JFXButton btn_facturation;
    @FXML
    private StackPane screen_container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Querry.getInstance().SelectDataFor(btn_facturation, btn_historique);
        ecran_remove(screen_container, manifest.GUI_FACTURE);
        btn_facturation.setOnAction((e) -> {
            ecran_remove(screen_container, manifest.GUI_FACTURE);
            Querry.getInstance().SelectDataFor(btn_facturation, btn_historique);

        });
        btn_historique.setOnAction((e) -> {
            ecran_remove(screen_container, manifest.GUI_HISTORIQUE);
            Querry.getInstance().SelectDataFor(btn_historique, btn_facturation);

        });

    }

    public void ecran_remove(StackPane pane, String ecran) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ecran));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(root);
        } catch (IOException ex) {
            ex.printStackTrace();
//            alerteErreur("error", ex.getMessage());
        }
    }
}
