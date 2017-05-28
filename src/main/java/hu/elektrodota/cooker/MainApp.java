package hu.elektrodota.cooker;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApp extends Application {
     static Service service;
    @Override
    public void start(Stage stage) throws Exception {
        service=new Service();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainSceneController controller = fxmlLoader.<MainSceneController>getController();
        controller.setSr(service);
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(this::close);
        stage.setScene(scene);
        stage.show();
        
        stage.setResizable(false);
    }
 
    public  void close(WindowEvent e)
    {
        service.getFr().getFactory().close();
        Platform.exit();
        System.exit(0);
        
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

  

}
