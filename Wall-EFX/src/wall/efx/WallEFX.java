/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall.efx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WallEFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("FXMLDatos.fxml"));
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setTitle("WALL-E");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
