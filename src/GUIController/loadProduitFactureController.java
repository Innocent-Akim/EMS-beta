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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Vulembere
 */
public class loadProduitFactureController implements Initializable {

    @FXML
    private Label txtId;
    @FXML
    private Label txtNumero;
    @FXML
    private Label txtDesignation;
    @FXML
    private TextField txtQte;
    @FXML
    private TextField txtPU;
    @FXML
    private Label txtPT;

    public static String numero, designation, qte, pu, pt, id;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDesignation.setText(designation);
        txtId.setText(id);
        txtQte.setText(qte);
        txtPU.setText(pu);
        txtPT.setText(pt);
        txtNumero.setText(numero);
//        event();
    }

    @FXML
    private void select(MouseEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
        Facturation.deleteFact(Integer.valueOf(txtNumero.getText()) - 1);
        Facturation.fact.loadDetailleFact(FACTURATIONController.liste_detaille_);
    }

    @FXML
    private void setAmount(ActionEvent event) {
        Facturation.updateFact((Integer.valueOf(txtNumero.getText()) - 1), Float.valueOf(txtPU.getText()), Float.valueOf(txtQte.getText()));
    }

}
