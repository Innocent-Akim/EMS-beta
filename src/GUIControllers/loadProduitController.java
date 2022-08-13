/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIControllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import mlinzifact.Print;
import mlinzifact.Vars;

/**
 * FXML Controller class
 *
 * @author ISDR
 */
public class loadProduitController implements Initializable {

    @FXML
    private JFXButton btndelete;
    @FXML
    private JFXButton modfierbtn;
    @FXML
    private Text designation;
    @FXML
    private Label qte;
    @FXML
    private Label pu;
    @FXML
    private Label devise;
    @FXML
    private Label code;
    @FXML
    private JFXButton printBarcode;
    public static String qteString, designationString, puString, codeString, codeBarString, dateString;
    @FXML
    private Label barcode;
    @FXML
    private Label dateLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        designation.setText(designationString.trim());
        qte.setText(qteString.trim());
        pu.setText(puString.trim());
        code.setText(codeString.trim());
        barcode.setText(codeBarString.trim());
        dateLbl.setText(dateString);
        printBarcode.setOnAction((action) -> {
            Print.Imprimer("SELECT * FROM produit WHERE barcode='" + barcode.getText() + "' AND id_entrep='" + Vars.USER_ID_ENTREP + "'", "BarCode");
        });

    }

}
