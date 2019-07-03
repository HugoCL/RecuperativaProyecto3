package wall.efx;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hugoc
 */
public class FXMLDatosController implements Initializable {
    
    public static int f;
    public static int c;
    public static int b;
    @FXML public AnchorPane root;
    @FXML public TextField filas;
    private int cantFilas=0;
    @FXML public TextField columnas;
    private int cantColumnas=0;
    @FXML public TextField bombas;
    private int cantBombas=0;
    @FXML public Button continuar;
    private String textoError1="";
    private String textoError2="";
    
    @FXML
    public void ingresarDatos(){
        //REVISAR - try/catch para validar que sean numeros y no otroos caracteres
        if(filas.getText().isEmpty() || columnas.getText().isEmpty() || bombas.getText().isEmpty()){
            alerta("NO HA INGRESADO", "TODOS LOS DATOS.");
            filas.clear();
            columnas.clear();
            bombas.clear();
        }
        else{
            if(validarTipo(filas.getText())){
                cantFilas=Integer.parseInt(filas.getText());
                if(cantFilas<10 || cantFilas>20){
                    textoError1="LA CANTIDAD DE FILAS";
                    textoError2="INGRESADAS NO ES VALIDA.";
                    filas.clear();
                    columnas.clear();
                    bombas.clear();
                    alerta(textoError1, textoError2);
                }
                else{
                    if(validarTipo(columnas.getText())){
                        cantColumnas=Integer.parseInt(columnas.getText());
                        if(cantColumnas<10 || cantColumnas>30){
                            textoError1="LA CANTIDAD DE COLUMNAS";
                            textoError2="INGRESADAS NO ES VALIDA.";
                            filas.clear();
                            columnas.clear();
                            bombas.clear();
                            alerta(textoError1, textoError2);
                        }
                        else{
                            if(validarTipo(bombas.getText())){
                                cantBombas=Integer.parseInt(bombas.getText());
                                if(cantBombas<0 || cantBombas>((cantFilas*cantColumnas)-3)){
                                    textoError1="LA CANTIDAD DE BOMBAS";
                                    textoError2="INGRESADAS NO ES VALIDA.";
                                    filas.clear();
                                    columnas.clear();
                                    bombas.clear();
                                    alerta(textoError1, textoError2);
                                }
                                else{
                                    System.out.println("filas "+cantFilas+"columnas "+ cantColumnas+ "bombas "+cantBombas);
                                    FXMLDatosController.f=cantFilas;
                                    FXMLDatosController.c=cantColumnas;
                                    FXMLDatosController.b=cantBombas;
                                    SceneHandler.CargarVista(root, getClass().getResource("FXMLPantallaPrincipal.fxml"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @FXML
    public void alerta(String mensaje1, String mensaje2){
        Stage emergente = new Stage();
        
        emergente.initModality(Modality.APPLICATION_MODAL);
        emergente.setTitle("WALL E - ¡ERROR!");
        emergente.setWidth(250);
        emergente.setHeight(150);
        emergente.setResizable(false);
        
        Label label = new Label();
        Label label2 = new Label();
        label.setText(mensaje1);
        label2.setText(mensaje2);
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.getChildren().addAll(label2);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        emergente.setScene(scene);
        emergente.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public boolean validarTipo(String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        }
        catch(NumberFormatException nfe){
            filas.clear();
            columnas.clear();
            bombas.clear();
            alerta("LOS DATOS INGRESADOS", "NO SON COMPATIBLES.");
            System.out.println("ERROR DE TIPO DE DATO: LOS DATOS INGRESADOS NO CORRESPONDEN POR COMPLETO A NUMEROS.");
            return false;
        }
    }
    
    public int getCantFilas() {
        return cantFilas;
    }

    public int getCantBombas() {
        return cantBombas;
    }

    public int getCantColumnas() {
        return cantColumnas;
    }
}
