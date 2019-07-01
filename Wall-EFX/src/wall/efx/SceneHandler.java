/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall.efx;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Carolyn
 */
public class SceneHandler {
    
    static public void CargarVista(Pane viewRoot, URL resource){
        Stage stage = (Stage) viewRoot.getScene().getWindow();
        Parent root=null;        
        try {
            root = FXMLLoader.load(resource);
        } catch (IOException ex) {
            System.out.println("NO SE PUEDE MOSTRAR "+resource.toString());
            Logger.getLogger(SceneHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(5);
        stage.setY(5);
    }
    
}