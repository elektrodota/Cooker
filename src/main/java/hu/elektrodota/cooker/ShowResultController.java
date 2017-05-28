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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author elefank
 */
public class ShowResultController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextArea resultArea;
    Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    public void setResultArea(String resultArea) {
        this.resultArea.setText(resultArea);
    }
    @FXML
    Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backButton.setOnAction(this::backAction);

    }

    public void backAction(ActionEvent e) {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Searcher.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            SearcherController controller = fxmlLoader.<SearcherController>getController();
            controller.setService(service);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
