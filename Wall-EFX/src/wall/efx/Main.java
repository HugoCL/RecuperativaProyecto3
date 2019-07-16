package wall.efx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    public Main(){
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("Salvando a Wall-E 2.0");
        Parent root = FXMLLoader.load(getClass().getResource("WallE2.fxml"));
        Scene escena = new Scene(root, 1180, 675);
        primaryStage.setScene(escena);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
