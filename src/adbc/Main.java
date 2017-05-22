package adbc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.Preferences;

public class Main extends Application {

    @FXML
    private
    AnchorPane rootPane;

    public static final String ADB_PATH_KEY = "adb_path";
    private static Stage adbStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        adbStage = primaryStage;
        Controller controller = new Controller();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.setRoot(rootPane);
        Parent parent = fxmlLoader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("ADB Connector");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("adbc.png"))));
        primaryStage.show();
    }

    static String openChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Android SDK directory");
        File adbDir =  directoryChooser.showDialog(adbStage);

        if(adbDir != null) {
            return adbDir.getAbsolutePath();
        }
        return "";
    }


    public static void main(String[] args) {
        launch(args);
    }
}
