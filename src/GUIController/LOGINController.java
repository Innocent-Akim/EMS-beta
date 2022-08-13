/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Models.Login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import mlinzifact.MlinziFact;
import mlinzifact.View;
import mlinzifact.dbo;
import mlinzifact.manifest;
import mlinzifact.synchro;

/**
 * FXML Controller class
 *
 * @author Vulembere
 */
public class LOGINController implements Initializable {

    @FXML
    private TextField txtPsedo;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button BtnConnecter;
    @FXML
    private Hyperlink btnCreateAccount;
    @FXML
    private Button btnDelete;

    public static Login login = null;
    @FXML
    private StackPane statckpan;
    @FXML
    private Button btnConfig;

    public static StackPane statckpan_;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statckpan_ =statckpan;
        if(dbo.Con()!=null){
            synchro.start();
        } 
    }

    @FXML
    private void seConnecter(ActionEvent event) throws IOException {
        login = new Login(txtPsedo.getText(), txtPassword.getText());
        if (login.log()) {
            MlinziFact.public_stage_.hide();
            View.c.createWindow(manifest.GUI_PRINCIPALE, manifest.APP_TITRE, null, Boolean.TRUE, Boolean.TRUE, StageStyle.DECORATED, Boolean.TRUE);
        } else {
            System.out.println("Error");
        }

    }

    @FXML
    private void CreateAccount(ActionEvent event) {

    }

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void config(ActionEvent event) {
        View.c.setContaint(statckpan, manifest.CONFIG_SERVEUR, true);
    }

}
