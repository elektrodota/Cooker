/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author elefank
 */
public class SearcherController implements Initializable {

    @FXML
    Button backButton, detailButton, searchButton;
    @FXML
    TableView table;
    @FXML
    TextField recipeName, ingredientName;

    /**
     * Initializes the controller class.
     */
    Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Receptek, String> name = new TableColumn<>("Recipe name");
        name.setCellValueFactory(new PropertyValueFactory<Receptek, String>("receptNev"));
        name.setPrefWidth(table.getPrefWidth() * 0.5);
        TableColumn<Receptek, String> desc = new TableColumn<>("Recipe description");
        desc.setCellValueFactory(new PropertyValueFactory<Receptek, String>("receptLeiras"));
        desc.setPrefWidth(table.getPrefWidth() * 0.5);

        table.getColumns().addAll(name, desc);
        backButton.setOnAction(this::backAction);
        detailButton.setOnAction(this::detailAction);
        searchButton.setOnAction(this::searchAction);
    }

    private void backAction(ActionEvent e) {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            MainSceneController controller = fxmlLoader.<MainSceneController>getController();
            controller.setSr(service);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearcherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void detailAction(ActionEvent e) {
        Receptek selectedItem = (Receptek) table.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            List<Receptek> searchNamebyName = service.search(selectedItem.getReceptNev(), null);
            selectedItem = searchNamebyName.get(0);
            String searchEverything = service.getBackTheRecipe(selectedItem.getReceptId());
            try {
                Stage stage = (Stage) backButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowResult.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                ShowResultController controller = fxmlLoader.getController();
                controller.setResultArea(searchEverything);
                controller.setService(service);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void searchAction(ActionEvent e) {
        table.getItems().remove(0, table.getItems().size());
        List<Receptek> r = service.search(recipeName.getText(), ingredientName.getText());
        table.getItems().addAll(r);

    }
}
