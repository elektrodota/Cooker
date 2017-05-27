package hu.elektrodota.cooker;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FXMLController implements Initializable {

    @FXML
    TableView<ReceptHozzavalok> table;
    @FXML
    AnchorPane ap;
    @FXML
    ComboBox units;
    @FXML
    TextField IngredientField, quantityField;
    @FXML
    Button addButton, removeButton, next, backButton;
    FullRecept fullrecept;

    public FullRecept getFullrecept() {
        return fullrecept;
    }

    public void setFullrecept(FullRecept fullrecept) {
        this.fullrecept = fullrecept;
        for (int i = 0; i < fullrecept.getReceptHozzavalok().size(); i++) {
            table.getItems().add(fullrecept.getReceptHozzavalok().get(i));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<ReceptHozzavalok, String> ing = new TableColumn<>("Ingredients");
        ing.setCellValueFactory(new PropertyValueFactory<ReceptHozzavalok, String>("hozzavaloNeve"));
        ing.setPrefWidth(table.getPrefWidth() * 0.5);
        TableColumn<ReceptHozzavalok, String> quant = new TableColumn<>("Quantity (with unit)");
        quant.setCellValueFactory(new PropertyValueFactory<ReceptHozzavalok, String>("mennyiseg"));
        quant.setPrefWidth(table.getPrefWidth() * 0.5);

        table.getColumns().addAll(ing, quant);
        addButton.setOnAction(this::addEvent);
        removeButton.setOnAction(this::removeEvent);
        ObservableList<String> items = FXCollections.observableArrayList(
                "g", "ml", "Teaspoon", "Dessertspoon", "Tablespoon");
        units.setItems(items);
        next.setOnAction(this::nextEvent);
        backButton.setOnAction(this::backEvent);
    }

    private void backEvent(ActionEvent e) {
        try {
            Stage window = (Stage) backButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RecipeDescription.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            window.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nextEvent(ActionEvent e) {
        try {
            Stage window = (Stage) next.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Steps.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            StepsController controller = fxmlLoader.<StepsController>getController();
            List<ReceptHozzavalok> items = table.getItems();
            ArrayList<ReceptHozzavalok> lista;
            if (items instanceof ArrayList<?>) {
                lista = (ArrayList<ReceptHozzavalok>) items;
            } else {
                lista = new ArrayList<>(items);
            }
            fullrecept.setReceptHozzavalok(lista);

            for (int i = 0; i < items.size(); i++) {

                Hozzavalok h = new Hozzavalok();
                h.setHozzavaloNeve(items.get(i).getHozzavaloNeve());
                fullrecept.getHozzavalok().add(h);

            }
            controller.setRecipe(fullrecept);
            window.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addEvent(ActionEvent e) {
        ReceptHozzavalok ing = new ReceptHozzavalok();
        String selectedItem = (String) units.getSelectionModel().getSelectedItem();
        String name = IngredientField.getText();
        String quantity = quantityField.getText();
        try {
            double parseDouble = Double.parseDouble(quantity);
        } catch (NumberFormatException ex) {
            return;
        }
        if (name != null && quantity != null && selectedItem != null) {
            ing.setHozzavaloNeve(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            ing.setMennyiseg(quantity + " " + selectedItem);

            table.getItems().add(ing);
            IngredientField.clear();
            quantityField.clear();
        }
    }

    private void removeEvent(ActionEvent e) {
        ReceptHozzavalok selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            table.getItems().remove(selectedItem);
        }
    }
}
