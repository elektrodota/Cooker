package hu.elektrodota.cooker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author elefank
 */
public class RecipeDescriptionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField name,preparationField;
    @FXML
    private TextArea description;
    Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

    @FXML
    public void nextAction() {
        try {
            String text=preparationField.getText();
            if(!text.matches("[0-9]+") && text.length() > 1)
                return;
            Stage window = (Stage) name.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLController controller = fxmlLoader.<FXMLController>getController();
            Receptek recept = new Receptek();
            recept.setReceptLeiras(description.getText());
            recept.setReceptNev(name.getText());
            recept.setElkeszitesiIdo(Integer.parseInt(preparationField.getText()));
            service.getFr().setRecept(recept);
            controller.setService(service);
            window.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(RecipeDescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void backAction() {
        try {
            Stage window = (Stage) name.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
            
            Parent root=(Parent) fxmlLoader.load();
            MainSceneController controller = fxmlLoader.<MainSceneController>getController();
            controller.setSr(service);
            window.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(RecipeDescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
