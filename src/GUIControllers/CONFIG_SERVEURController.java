/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import GUIController.LOGINController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static mlinzifact.Preference.pref;
import mlinzifact.View;
import mlinzifact.cls;
import mlinzifact.fichiers;
import mlinzifact.manifest;

/**
 * FXML Controller class
 *
 * @author Vulembere
 */
public class CONFIG_SERVEURController implements Initializable {

    @FXML
    private TextField txtServeur;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtBaseDesDonnees;
    @FXML
    private TextField txtUserName;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnConfig;
    @FXML
    private Button BtnConnecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtBaseDesDonnees.setText(pref.getBd());
        txtPassword.setText(pref.getPassword());
        txtServeur.setText(pref.getHost());
        txtUserName.setText(pref.getUser());
    }

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void config(ActionEvent event) {
        View.c.setContaint(LOGINController.statckpan_, manifest.GUI_LOGIN, true);
    }

    @FXML
    private void seConnecter(ActionEvent event) {
        try {
            pref.setBd(txtBaseDesDonnees.getText());
            pref.setHost(txtServeur.getText());
            pref.setUser(txtUserName.getText());
            pref.setPassword(txtPassword.getText());
            if(cls.c.setAlert("Veiller relancer l'application pour prendre en compte les modifications", Alert.AlertType.CONFIRMATION)){
                System.exit(0);
            }
        } catch (Exception e) {
            fichiers.error(e.getMessage());
        }

    }

}
