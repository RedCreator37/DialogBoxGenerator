package redcreator37;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class
 */
public class DialogMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(this.getClass().getResource("MainWindow.fxml").openStream());
        primaryStage.setTitle("Dialog Box Generator");
        primaryStage.setScene(new Scene(root, 490, 302));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
        System.gc();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
