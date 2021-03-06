/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author elefank
 */
public class MainSceneController implements Initializable {

    @FXML
    private Button findRecipe, newRecipe, quit;
    Service sr;

    public void setSr(Service sr) {
        this.sr = sr;
    }

    public Service getSr() {
        return sr;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    private void closeEvent(Event e) {

        Platform.exit();
    }

    public void setupScene() {
        quit.getScene().getWindow().setOnCloseRequest(this::closeEvent);
    }

    @FXML
    public void findAction() {

        this.setupScene();
        try {
            Stage stage = (Stage) newRecipe.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Searcher.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SearcherController controller = fxmlLoader.<SearcherController>getController();
            controller.setService(sr);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void newAction() {

        this.setupScene();
        try {

            Stage stage = (Stage) newRecipe.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RecipeDescription.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            RecipeDescriptionController controller = fxmlLoader.<RecipeDescriptionController>getController();
            controller.setService(sr);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void quitAction() {

        this.setupScene();
        Platform.exit();
    }

}
