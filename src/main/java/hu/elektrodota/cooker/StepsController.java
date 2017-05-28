/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elektrodota.cooker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author elefank
 */
public class StepsController implements Initializable {

    @FXML
    private TableView<ReceptLepesek> table;
    @FXML
    private AnchorPane ap;
    @FXML
    private HBox bottom;
    @FXML
    private Button AddButton, DeleteButton, Next, Back;
    @FXML
    private TextField description;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        for(int i=0;i<service.getFr().getReceptLepesek().size();i++)
        {
            table.getItems().add(service.getFr().getReceptLepesek().get(i));
        }
    }
    
    Service service;
    
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn<ReceptLepesek, String> step = new TableColumn<>("Step");

        step.setCellValueFactory(new PropertyValueFactory<ReceptLepesek, String>("lepesSzam"));
        TableColumn<ReceptLepesek, String> description = new TableColumn<>("Description");

        description.setCellValueFactory(new PropertyValueFactory<ReceptLepesek, String>("lepesLeiras"));
        table.getColumns().addAll(step, description);
        AddButton.setOnAction(this::addAction);
        DeleteButton.setOnAction(this::removeEvent);
        Back.setOnAction(this::backEvent);
        Next.setOnAction(this::loadUpEvent);
    }

    public void loadUpEvent(ActionEvent e) {
        List<ReceptLepesek> items = table.getItems();
        ArrayList<ReceptLepesek> lista;
        if (items instanceof ArrayList<?>) {
            lista = (ArrayList<ReceptLepesek>) items;
        } else {
            lista = new ArrayList<>(items);
        }
        service.getFr().setReceptLepesek(lista);
        service.loadUp();
        Stage stage = (Stage) Next.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        
        Parent root;
        try {
            root = (Parent) fxmlLoader.load();
            MainSceneController controller = fxmlLoader.<MainSceneController>getController();
            controller.setSr(service);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(StepsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addAction(ActionEvent e) {
        String desc = description.getText();
        if (table.getItems().isEmpty()) {
            if (!desc.isEmpty()) {
                ReceptLepesek step = new ReceptLepesek();
                step.setLepesSzam(1);
                step.setLepesLeiras(desc);
                table.getItems().add(step);
            }
        } else {
            if (!desc.isEmpty()) {
                System.out.println(table.getItems().size());
                ReceptLepesek step = new ReceptLepesek();
                ObservableList<ReceptLepesek> items = table.getItems();
                long num = items.get(items.size() - 1).getLepesSzam() + 1;
                step.setLepesSzam(num);
                step.setLepesLeiras(desc);
                table.getItems().add(step);
            }

        }
        description.clear();
    }

    private void removeEvent(ActionEvent e) {
        ReceptLepesek selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            table.getItems().remove(selectedItem);
            ObservableList<ReceptLepesek> items = table.getItems();
            for (int i = 0; i < items.size(); i++) {
                items.get(i).setLepesSzam(i + 1);
            }
        }
    }

    private void backEvent(ActionEvent e) {
        try {
            Stage window = (Stage) Back.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLController controller = fxmlLoader.<FXMLController>getController();
            service.getFr().getReceptLepesek();
             service.getFr().setReceptLepesek(new ArrayList<ReceptLepesek>());
            List<ReceptLepesek> items = table.getItems();

            ArrayList<ReceptLepesek> lista;
            if (items instanceof ArrayList<?>) {
                lista = (ArrayList<ReceptLepesek>) items;
            } else {
                lista = new ArrayList<>(items);
            }

            service.getFr().setReceptLepesek(lista);

            controller.setService(service);
            window.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
