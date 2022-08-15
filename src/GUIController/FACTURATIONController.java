/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Models.Facturation;
import Models.Operations;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import mlinzifact.Print;
import mlinzifact.Querry;
import mlinzifact.Vars;
import mlinzifact.cls;
import mlinzifact.manifest;
import mlinzifact.synchro;

/**
 * FXML Controller class
 *
 * @author Vulembere
 */
public class FACTURATIONController implements Initializable {

    @FXML
    private DatePicker txtDate;
    @FXML
    private Label txtNumero;
    @FXML
    private TextField txtClient;
    @FXML
    private ListView<?> liste_detaille;
    @FXML
    private Label txtMontant;
    @FXML
    private Label txtLabDevise;
    @FXML
    private TextField txtMontantCash;
    @FXML
    private ComboBox<String> txtDevise;
    @FXML
    private Label txtdefault_defaut;
    @FXML
    private TextField txtMontantConverti;
    @FXML
    private TextField txtMontantReste1;
    @FXML
    private TextArea txtMontantTouteLettre;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField txtRecherche;
    @FXML
    private Button btnReload;

    @FXML
    private StackPane contenaire_home;

    public static Label txtMontant_;
    public static TextField txtClient_;
    public static ListView liste_detaille_;
    public static ListView public_listSesrch;
    public static StackPane contenaire_hone_;
    public static Label txtNumeroString;
    @FXML
    private ComboBox<String> txtEntrep;
    @FXML
    private Button btnProformat;
    @FXML
    private Button btnSynchro;
    @FXML
    private TextField txtSearchfacture;
    @FXML
    private ListView<?> loadProduit;
    @FXML
    private Label quantitedispo;
    @FXML
    private Label punitaire;
    @FXML
    private JFXListView<String> loadClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSearchfacture.requestFocus();
        Vars.loadEntrep(txtEntrep);
        Vars.defaultDevise();
        loadClient.setVisible(false);
//        txtNumeroString = txtNumero;
        contenaire_hone_ = contenaire_home;
        txtMontant_ = txtMontant;
//        public_listSesrch = searchFactureList;
        txtLabDevise.setText(" " + Vars.DEVISE_DEFAULT);
        liste_detaille_ = liste_detaille;
//        txtClient_ = txtClient;
        Vars.loadDevise(txtDevise);
        txtDate.setValue(LocalDate.now());
        txtdefault_defaut.setText(Vars.DEVISE_DEFAULT);
        txtDevise.getSelectionModel().select(Vars.DEVISE_DEFAULT);
//        searchFactureList.setVisible(false);
//        loadProduit.setVisible(false);
        Facturation.init(Vars.USER_ID_ENTREP);
        Facturation.fact.loadProduis(loadProduit, null);
        init();
        event();
    }

    void event() {
        txtSearchfacture.setOnKeyReleased((KeyEvent key) -> {
            try {
                String numero = null;
                if (txtNumero.getText().equals("000")) {
                    numero = Querry.getIdFacture("enteteFacture");
                    txtNumero.setText(numero.trim());
                }
                ResultSet rs = Querry.getData("SELECT * FROM `produit` WHERE qte>0 AND barcode= '" + txtSearchfacture.getText().trim() + "' OR nom= '" + txtSearchfacture.getText().trim() + "' AND id_entrep='" + Vars.USER_ID_ENTREP + "' ORDER BY nom DESC");
                if (rs.next()) {
                    Facturation.addFact(rs.getString("nom").toUpperCase(), rs.getString("id"), Float.valueOf(rs.getString("prix_max")), 1);
                    quantitedispo.setText(rs.getString("qte"));
                    punitaire.setText(rs.getString("prix_max"));
                    txtSearchfacture.requestFocus();
                    txtSearchfacture.clear();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        loadProduit.setOnMouseClicked((action) -> {
            try {
                String numero = null;
                if (txtNumero.getText().equals("000")) {
                    numero = Querry.getIdFacture("enteteFacture");
                    txtNumero.setText(numero.trim());
                }
                String barcode = loadProduit.getSelectionModel().getSelectedItem().toString().trim();
                ResultSet rs = Querry.getData("SELECT * FROM `produit` WHERE qte>0 AND barcode='" + barcode.substring(barcode.indexOf("||") + 2) + "' AND id_entrep='" + Vars.USER_ID_ENTREP + "' ORDER BY nom DESC");
                if (rs.next()) {
                    Facturation.addFact(rs.getString("nom").toUpperCase(), rs.getString("id"), Float.valueOf(rs.getString("prix_max")), 1);
                    quantitedispo.setText(rs.getString("qte"));
                    punitaire.setText(rs.getString("prix_max"));
                    txtSearchfacture.requestFocus();
                    txtSearchfacture.clear();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        txtClient.setOnKeyReleased((e) -> {
            loadClient.setVisible(true);
            loadClient.getItems().clear();
            ResultSet rs = Querry.getData("SELECT * FROM `clients` WHERE nom like '%" + txtClient.getText() + "%' ORDER BY nom ASC");
            try {
                while (rs.next()) {
                    loadClient.getItems().addAll(rs.getString("nom").trim());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        loadClient.setOnMouseClicked((action) -> {
            loadClient.setVisible(false);
            txtClient.setText(loadClient.getSelectionModel().getSelectedItem().trim());
        });
        loadClient.setOnMouseExited((action) -> {
            loadClient.setVisible(false);
        });

    }

    @FXML
    private void Rechercher(KeyEvent event) {
//        loadProduit.setVisible(true);
        Facturation.fact.loadProduis(loadProduit, txtRecherche.getText());
    }

    private void AddClient(ActionEvent event) throws IOException {
        Vars.create_name = 3;
    }

    @FXML
    private void reload(ActionEvent event) {
        txtRecherche.setText("");
        Facturation.removeAll();
        Facturation.init(Vars.USER_ID_ENTREP);

    }

    @FXML
    private void Print(ActionEvent event) {
        print("1");
    }

    void print(String etat) {

        if (!Vars.USER_ID_ENTREP.trim().isEmpty()) {

            if ((Float.parseFloat(txtMontant.getText()) > Float.parseFloat(txtMontantCash.getText()))) {
                if (txtClient.getText().equals("") || txtClient.getText().isEmpty()) {
                    cls.c.setAlert("Ajoutez d'abord les clients", Alert.AlertType.WARNING);
                } else {
                    initPrint(etat);
                }

            } else {
                initPrint(etat);
            }

        } else {
            cls.c.setAlert("Veuillez vous assurer de sélectionner le magasin ou la sicursale !", Alert.AlertType.WARNING);
        }
    }

    void initPrint(String etat) {
        try {
            String from = "70";
            String to = "57";
            String numero = Querry.getIdFacture("enteteFacture");
            String id = Querry.getId();
            if (!txtMontant.getText().trim().equals("0.0")) {
                if (Operations.saveFact(id, "VENTE", numero, numero, from, to, txtClient.getText(), txtdefault_defaut.getText(), txtDevise.getValue(), String.valueOf(Vars.DEVISE_TAUX), txtMontantCash.getText(), txtMontantConverti.getText(), txtMontantTouteLettre.getText(), Facturation.getMotif(), txtDate.getValue().toString(), txtMontantReste1.getText(), "1")) {
                    if (!txtMontantReste1.getText().equals("0")) {
                        String from_ = "70";
                        String to_ = "41";
                        String id_ = Querry.getId();
                        String numero_ = Querry.getIdFacture("enteteFacture");
                        Operations.saveFact(id_, "CREDIT", numero, numero_, from_, to_, txtClient.getText(), txtdefault_defaut.getText(), txtDevise.getValue(), String.valueOf(Vars.DEVISE_TAUX), txtMontant.getText(), txtMontantReste1.getText(), "--", Facturation.getMotif(), txtDate.getValue().toString(), txtMontantReste1.getText(), "1");
                    }
                    System.out.println(id);
                    Facturation.saveDetailles(id);
                    Map<String, String> map = new HashMap();
                    if (etat.equals("0")) {
                        map.put("etat", "FACTURE PROFORMAT");
                    } else {
                        map.put("etat", "FACTURE ");
                    }
                    map.put("img", manifest.RAPPORT_LOGO);
                    new Print().Imprimer("SELECT * FROM `v_facture` where id_operation='" + id + "'", "Facture", map, Boolean.TRUE);
                    Facturation.removeAll();
                    init();
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void convert(KeyEvent event) {
        setDevise();
    }

    @FXML
    private void Convert(ActionEvent event) {
        setDevise();
    }
    static float taux = 0;

    void setDevise() {
        if (cls.isNumeric(txtMontantCash.getText())) {
            taux = Querry.getValueFloat("select taux from devises where designation=?", txtDevise.getValue());
            float montant = Float.valueOf(txtMontantCash.getText());
            float montant_orgin = Float.valueOf(txtMontant.getText());
            float converti = montant / taux;
            txtMontantConverti.setText(String.valueOf(converti));
            txtMontantTouteLettre.setText(cls.NombreToString(converti, Vars.DEVISE_DESCRIPTION));
            txtMontantReste1.setText(String.valueOf(montant_orgin - converti));
        }
    }

    void init() {
        txtNumero.setText("000");
        txtMontantCash.setText("0.0");
        txtMontantConverti.setText("0.0");
        txtMontantReste1.setText("0.0");
        txtMontantTouteLettre.setText(null);
        punitaire.setText("0.0");
        quantitedispo.setText("0.0");
        quantitedispo.setText("0.0");
        punitaire.setText("0.0");
        synchro.start();
        Facturation.removeAll();
      Facturation.fact.loadDetailleFact(liste_detaille_);
    }

    @FXML
    private void selectEntrep(ActionEvent event) {
        Vars.USER_ID_ENTREP = Querry.getValue("SELECT id FROM `entreprise` where name=?", txtEntrep.getValue());
        Facturation.init(Vars.USER_ID_ENTREP);
        Facturation.fact.loadProduis(loadProduit, null);
    }

    @FXML
    private void Proformat(ActionEvent event) {
        print("0");
    }

    @FXML
    private void Synchronise(ActionEvent event) {
        if (cls.c.setAlert("Cette opération prendra un certain temps avant d'être relancée pour prendre en compte les nouvelles données.", Alert.AlertType.CONFIRMATION)) {
            synchro.start();
        }
    }
}
