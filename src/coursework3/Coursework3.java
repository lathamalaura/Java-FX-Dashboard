package coursework3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Coursework3 extends Application {    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("Coursework3.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Coursework3.class.getResource("Style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coursework3");
        primaryStage.show();
 
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
