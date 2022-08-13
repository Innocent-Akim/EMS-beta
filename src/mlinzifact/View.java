/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;
 
import GUIController.FACTURATIONController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Vulembere
 */
public class View {

    public static final HashMap<String, Node> SCREENS = new HashMap<>();
    public static Node currentView = null;
    public static JFXDialog dialog;
    public static JFXDialog dialog2;

    public static void DialogClose() {

    }

    public Node get(String view, boolean init) throws IOException {

        if (init) {
            SCREENS.remove(view);
        }
        if (!SCREENS.containsKey(view)) {

            if (view == null) {
                SCREENS.put(view, get(manifest.GUI_ERROR_PAGE, init));
            } else {
                currentView = FXMLLoader.load(getClass().getResource(view));
                SCREENS.put(view, currentView);
            }
        } else {
            SCREENS.put(view, FXMLLoader.load(getClass().getResource(view)));
        }

        return SCREENS.get(view);
    }

    public void remode(String remove) {
        SCREENS.remove(remove);
    }

    public void setContaint(Node Contrainaire, String interfaces, boolean init) {
        try {
            Node child = this.get(interfaces, init);
            if (Contrainaire instanceof StackPane) {
                StackPane contain_area = (StackPane) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof VBox) {
                VBox contain_area = (VBox) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof AnchorPane) {
                AnchorPane contain_area = (AnchorPane) Contrainaire;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (Contrainaire instanceof ScrollPane) {
                ScrollPane contain_area = (ScrollPane) Contrainaire;
                contain_area.setContent(child);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void getDialog(String interfaces, boolean remove) throws IOException {
        if (remove) {
            SCREENS.remove(interfaces);
        }
        JFXDialogLayout dl = new JFXDialogLayout();
        Node node = this.get(interfaces, remove);
        dl.setBody(node);

        dialog = new JFXDialog(FACTURATIONController.contenaire_hone_, dl, JFXDialog.DialogTransition.CENTER, true);
        dialog.show(FACTURATIONController.contenaire_hone_);

    }

    public void createWindow(String root, String title, Stage parentStage, Boolean resizable, Boolean returnLogin, StageStyle style, Boolean maximised) throws IOException {

        if (parentStage != null) {
            MlinziFact.public_stage_ = parentStage;
           MlinziFact.public_stage_.initModality(Modality.APPLICATION_MODAL);
        } else {
            MlinziFact.public_stage_ = new Stage(StageStyle.DECORATED);
        }
       MlinziFact.public_stage_.setResizable(resizable);
        MlinziFact.public_stage_.setTitle(title);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(root)));
        MlinziFact.public_stage_.initStyle(style);
        MlinziFact.public_stage_.setScene(scene);
        MlinziFact.public_stage_.setMaximized(maximised);
       MlinziFact.public_stage_.getIcons().add(new javafx.scene.image.Image(manifest.APP_ICON));
       MlinziFact.public_stage_.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.initStyle(StageStyle.DECORATED);
            alert.setHeaderText("Voulez-vous vous déconnecter de cette interface ?");
            Stage stages = (Stage) alert.getDialogPane().getScene().getWindow();
            stages.getIcons().add(new Image(this.getClass().getResource(manifest.APP_ICON).toString()));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (returnLogin) {
                    try {
                        this.createWindow(manifest.GUI_LOGIN, manifest.APP_TITRE, null, Boolean.FALSE, Boolean.TRUE, StageStyle.UNDECORATED, false);
                    } catch (IOException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                event.consume();
            }
        });
        MlinziFact.public_stage_.show();
    }

    public static View c = new View();
}
