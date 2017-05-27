package hu.elektrodota.cooker;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->this.closeEvent());
        stage.setResizable(false);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void closeEvent() {
        Platform.exit();
    }

}
