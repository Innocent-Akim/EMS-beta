/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlinzifact;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Vulembere
 */
public class MlinziFact extends Application {

    public static Stage public_stage_;

    @Override
    public void start(Stage primaryStage) {
        dbo.init();
        try {
            StackPane root = new StackPane();
            root.getChildren().add(FXMLLoader.load(getClass().getResource(manifest.GUI_LOGIN)));
            Scene scene = new Scene(root);
            primaryStage.setTitle(manifest.APP_TITRE);
            primaryStage.getIcons().add(new Image(manifest.APP_ICON));
            primaryStage.setScene(scene);
            public_stage_ = primaryStage;
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MlinziFact.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
