/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Models.Facturation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import mlinzifact.Print;
import mlinzifact.Querry;
import mlinzifact.Vars;

/**
 * FXML Controller class
 *
 * @author Vulembere
 */
public class LoadArticleVenteController implements Initializable {

    @FXML
    private Label txtId;
    @FXML
    private Label txtNum;
    @FXML
    private Label txtDesignation;
    @FXML
    private Label txtPrix;

    public static String  numero, designation, prix, barcode, description, quantite;
    @FXML
    private Button btnAdd;
    @FXML
    private Label txtBarcode;
    @FXML
    private Text txtDesciption;
    @FXML
    private Label txtQuantite;
    @FXML
    private Label printCodeBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDesignation.setText(designation);
//        txtId.setText(id);
        txtNum.setText(numero);
        txtPrix.setText(prix);
        txtBarcode.setText(barcode);
        txtDesciption.setText(description);
        txtQuantite.setText(quantite);
        printCodeBar.setOnMouseClicked((e) -> {
            Print.Imprimer("SELECT * FROM produit WHERE barcode='" + txtBarcode.getText() + "' AND id_entrep='" + Vars.USER_ID_ENTREP + "'", "BarCode");
        });
    }

    @FXML
    private void Ajouter(ActionEvent event) {
        if (FACTURATIONController.txtNumeroString.getText().trim().equals("000")) {
            FACTURATIONController.txtNumeroString.setText(Querry.getIdFacture("enteteFacture"));
        }
        Facturation.addFact(txtDesignation.getText().toUpperCase(), txtId.getText(), Float.valueOf(txtPrix.getText()), 1);

    }

}
