/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import GUIController.FACTURATIONController;
import Models.Facturation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mlinzifact.Querry;
import mlinzifact.Vars;

/**
 * FXML Controller class
 *
 * @author ISDR
 */
public class SearchFactureController implements Initializable {

    @FXML
    private JFXButton txtIDFacture;
    @FXML
    private Label txtDesignation;

    public static String id, designation;
    @FXML
    private AnchorPane cardFacture;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDesignation.setText(designation.trim());
        txtIDFacture.setText(id.trim());
        cardFacture.setOnMouseClicked((e) -> {
            delete();
        });
        // TODO
    }

    void delete() {
        JFXButton b1 = new JFXButton("OK");
        JFXButton b2 = new JFXButton("Annuler");
        JFXDialogLayout Layout = new JFXDialogLayout();
        Layout.setHeading(new Label("Voulez-vous annuler la facture pour recommencer ?"));
        b2.setStyle("-fx-background-color: #fff;-fx-text-fill: #b2d2a4;");
        b2.setFont(Font.font("Century Gothic", FontWeight.BOLD, 12));
        b1.setStyle("-fx-background-color: #b2d2a4;-fx-text-fill: #fff;");
        b1.setFont(Font.font("Century Gothic", FontWeight.BOLD, 12));
        JFXDialog dialog = new JFXDialog(FACTURATIONController.contenaire_hone_, Layout, JFXDialog.DialogTransition.CENTER);
        b1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            FACTURATIONController.liste_detaille_.getItems().clear();
            Facturation.removeAll();
            FACTURATIONController.public_listSesrch.setVisible(false);
            ResultSet rs = Querry.getData("SELECT * FROM `v_facture` WHERE numero='" + txtIDFacture.getText() + "' AND id_entrep='" + Vars.USER_ID_ENTREP + "'");
            try {
                while (rs.next()) {
                    Facturation.addFact(rs.getString("nom").toUpperCase(), rs.getString("numero"), Float.valueOf(rs.getString("pu")), rs.getInt("qte"));
                    FACTURATIONController.txtNumeroString.setText(rs.getString("numero"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchFactureController.class.getName()).log(Level.SEVERE, null, ex);
            }
            dialog.close();
        });
        b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            dialog.close();
        });
        Layout.setActions(b1, b2);
        dialog.show();

    }

}
